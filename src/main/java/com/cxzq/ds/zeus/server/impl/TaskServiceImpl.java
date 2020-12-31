package com.cxzq.ds.zeus.server.impl;

import com.cxzq.ds.zeus.config.DataSourceMap;
import com.cxzq.ds.zeus.config.NamedThreadFactory;
import com.cxzq.ds.zeus.enums.taskStatusEnum;
import com.cxzq.ds.zeus.enums.taskTypeEnum;
import com.cxzq.ds.zeus.mapper.TableInfoMapper;
import com.cxzq.ds.zeus.mapper.TypeInfoMapper;
import com.cxzq.ds.zeus.pojo.SourceConfig;
import com.cxzq.ds.zeus.pojo.SubTaskInfo;
import com.cxzq.ds.zeus.server.SourceService;
import com.cxzq.ds.zeus.server.TaskInfoService;
import com.cxzq.ds.zeus.server.TaskService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author yangkai
 */
@Service
public class TaskServiceImpl
        implements TaskService
{

    @Autowired
    private SourceService sourceService;

    @Autowired
    private TaskInfoService taskInfoService;

    @Autowired
    private TableInfoMapper tableInfoMapper;

    @Autowired
    private TypeInfoMapper typeInfoMapper;

    /**
     * 设置核心池大小
     */
    int corePoolSize = 5;
    /**
     * 设置线程池最大能接受多少线程
     */
    int maximumPoolSize = 6;
    /**
     * 当前线程数大于corePoolSize、小于maximumPoolSize时，超出corePoolSize的线程数的生命周期
     */
    long keepActiveTime = 20;
    /**
     * 设置时间单位，秒
     */
    TimeUnit timeUnit = TimeUnit.SECONDS;

    @Override
    public void colInfoStorageTask(String mainTaskId)
    {
        //读源任务获取数据库连接
        getColInfoStorageDataSource(mainTaskId);
        //读源任务
        colInfoStorage(mainTaskId);
        //将主任务表状态置为源表读取任务完成
        taskInfoService.updateMainTaskStatus(mainTaskId);
        //读源任务完成触发建表任务
        createTableTask(mainTaskId);
    }

    /**
     * 读源任务数据库连接
     *
     * @param mainTaskId 主任务
     */
    private void getColInfoStorageDataSource(String mainTaskId)
    {
        //获取jdbc连接信息
        Map<Object, Connection> dataSourceMap = DataSourceMap.getInstance();
        //主任务ID查询读源任务 根据目标源ID去重
        List<SubTaskInfo> list = taskInfoService.getGroupColInfoStorageTasks(mainTaskId);
        if (list != null && !list.isEmpty()) {
            for (SubTaskInfo subTaskInfo : list) {
                Connection connection = dataSourceMap.get(subTaskInfo.getSourceId());
                if (connection == null) {
                    sourceService.connectDataSource(subTaskInfo.getSourceId(), subTaskInfo.getId(), subTaskInfo.getTaskId(), taskTypeEnum.READING_SOURCE_TASK.getCode());
                }
                else {
                    colInfoIsValid(subTaskInfo, connection);
                }
            }
        }
    }

    /**
     * 校验连接是否有效
     */
    private void colInfoIsValid(SubTaskInfo subTaskInfo, Connection connection)
    {
        //校验连接是否有效，如果无效关闭连接后重新连接
        try {
            boolean bean = connection.isValid(10);
            if (!bean) {
                sourceService.connectDataSource(subTaskInfo.getSourceId(), subTaskInfo.getId(), subTaskInfo.getTaskId(), taskTypeEnum.READING_SOURCE_TASK.getCode());
            }
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
            taskInfoService.updateReadingSourceTask(subTaskInfo.getTaskId(), null, subTaskInfo.getSourceId(), taskStatusEnum.FAIL.getCode(), throwables.toString());
            sourceService.delDataSource(subTaskInfo.getSourceId());
            try {
                connection.close();
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 读源任务
     *
     * @param mainTaskId 主任务ID
     */
    private void colInfoStorage(String mainTaskId)
    {
        //获取jdbc连接信息
        Map<Object, Connection> dataSourceMap = DataSourceMap.getInstance();
        List<SubTaskInfo> subTaskInfos = taskInfoService.getColInfoStorageTasks(mainTaskId);
        if (subTaskInfos != null && !subTaskInfos.isEmpty()) {
            String coreSize = System.getProperty("corePoolSize");
            if (StringUtils.isNotBlank(coreSize)) {
                corePoolSize = Integer.parseInt(coreSize);
            }
            String maxPoolSize = System.getProperty("maximumPoolSize");
            if (StringUtils.isNotBlank(maxPoolSize)) {
                maximumPoolSize = Integer.parseInt(maxPoolSize);
            }
            //设置线程池缓存队列的排队策略为FIFO，并且指定缓存队列大小
            BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(maximumPoolSize);
            //创建ThreadPoolExecutor线程池对象，并初始化该对象的各种参数
            ThreadPoolExecutor threadPool = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepActiveTime, timeUnit, workQueue, new NamedThreadFactory("读源任务"));
            CountDownLatch c;
            for (SubTaskInfo subTaskInfo : subTaskInfos) {
                SourceConfig sourceConfig = sourceService.getSourceConfig(subTaskInfo.getTargetId());
                Connection connection = dataSourceMap.get(subTaskInfo.getSourceId());
                try {
                    c = new CountDownLatch(1);
                    //队列中等待执行的任务数目小于
                    threadPool.execute(new ThreadColInfoStorageTask(connection, subTaskInfo, taskInfoService, tableInfoMapper, typeInfoMapper, sourceConfig.getDtype(), c));
                    if (threadPool.getPoolSize() == maximumPoolSize - 1) {
                        c.await();
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
            try {
                //等待所有线程执行完毕当前任务。
                threadPool.shutdown();
                boolean loop;
                do {
                    //等待所有线程执行完毕当前任务结束
                    loop = !threadPool.awaitTermination(2, TimeUnit.SECONDS);
                }
                while (loop);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        }
    }

    @Override
    public void createTableTask(String mainTaskId)
    {
        //获取jdbc连接信息
        Map<Object, Connection> dataSourceMap = DataSourceMap.getInstance();
        this.getCreateTableDataSource(mainTaskId);
        //获取需要完成的建表任务
        List<SubTaskInfo> subTaskInfos = taskInfoService.getCreateTableTas(mainTaskId);
        if (subTaskInfos != null && !subTaskInfos.isEmpty()) {
            String coreSize = System.getProperty("corePoolSize");
            if (StringUtils.isNotBlank(coreSize)) {
                corePoolSize = Integer.parseInt(coreSize);
            }
            String maxPoolSize = System.getProperty("maxPoolSize");
            if (StringUtils.isNotBlank(maxPoolSize)) {
                maximumPoolSize = Integer.parseInt(maxPoolSize);
            }
            //设置线程池缓存队列的排队策略为FIFO，并且指定缓存队列大小
            BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(maximumPoolSize);
            //创建ThreadPoolExecutor线程池对象，并初始化该对象的各种参数
            ThreadPoolExecutor threadPool = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepActiveTime, timeUnit, workQueue, new NamedThreadFactory("建表任务"));
            CountDownLatch c;
            for (SubTaskInfo subTaskInfo : subTaskInfos) {
                Connection connection = dataSourceMap.get(subTaskInfo.getTargetId());
                try {
                    c = new CountDownLatch(1);
                    threadPool.execute(new ThreadCreateTableTask(connection, subTaskInfo, taskInfoService, tableInfoMapper, c));
                    if (threadPool.getPoolSize() == maximumPoolSize - 1) {
                        c.await();
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
            try {
                //等待所有线程执行完毕当前任务。
                threadPool.shutdown();
                boolean loop;
                do {
                    //等待所有线程执行完毕当前任务结束
                    loop = !threadPool.awaitTermination(2, TimeUnit.SECONDS);
                }
                while (loop);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        }
    }

    /**
     * 建表任务获取数据源
     *
     * @param mainTaskId 主任务ID
     */
    private void getCreateTableDataSource(String mainTaskId)
    {
        //获取jdbc连接信息
        Map<Object, Connection> dataSourceMap = DataSourceMap.getInstance();
        //主任务ID查询需要建表并且源信息任务已完成的 根据目标源ID去重
        List<SubTaskInfo> list = taskInfoService.getGroupCreateTableTas(mainTaskId);
        if (list != null && !list.isEmpty()) {
            for (SubTaskInfo subTaskInfo : list) {
                Connection connection = dataSourceMap.get(subTaskInfo.getTargetId());
                if (connection == null) {
                    sourceService.connectDataSource(subTaskInfo.getTargetId(), subTaskInfo.getId(), subTaskInfo.getTaskId(), taskTypeEnum.CREATE_TABLE_TASK.getCode());
                }
                else {
                    //校验连接是否有效，如果无效关闭连接后重新连接
                    createTableIsValid(subTaskInfo, connection);
                }
            }
        }
    }

    /**
     * 校验建表连接是否有效
     */
    private void createTableIsValid(SubTaskInfo subTaskInfo, Connection connection)
    {
        try {
            boolean bean = connection.isValid(10);
            if (!bean) {
                sourceService.connectDataSource(subTaskInfo.getTargetId(), subTaskInfo.getId(), subTaskInfo.getTaskId(), taskTypeEnum.CREATE_TABLE_TASK.getCode());
            }
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
            taskInfoService.updateCreateTableTask(subTaskInfo.getTaskId(), null, subTaskInfo.getTargetId(), taskStatusEnum.FAIL.getCode(), throwables.toString());
            sourceService.delDataSource(subTaskInfo.getSourceId());
            try {
                connection.close();
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
