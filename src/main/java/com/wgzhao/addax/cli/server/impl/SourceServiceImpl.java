package com.wgzhao.addax.cli.server.impl;

import com.wgzhao.addax.cli.config.DataSourceMap;
import com.wgzhao.addax.cli.enums.taskStatusEnum;
import com.wgzhao.addax.cli.enums.taskTypeEnum;
import com.wgzhao.addax.cli.mapper.SourceConfigMapper;
import com.wgzhao.addax.cli.pojo.SourceConfig;
import com.wgzhao.addax.cli.server.SourceService;
import com.wgzhao.addax.cli.server.TaskInfoService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Base64;
import java.util.Map;

/**
 * @author yangkai
 */
@Service
public class SourceServiceImpl
        implements SourceService
{
    @Autowired
    private SourceConfigMapper sourceConfigMapper;

    @Autowired
    private TaskInfoService taskInfoService;

    @Override
    public void connectDataSource(String sourceId, String taskId, String mainTaskId,
            String taskType)
    {
        //查询数据源信息
        SourceConfig sourceConfig = sourceConfigMapper.selectByPrimaryKey(sourceId);
        if (sourceConfig != null) {
            //获取数据源连接
            try {
                String pass = null;
                //判断是否有密码，如果有密码进行解密
                if (StringUtils.isNotBlank(sourceConfig.getPass())) {
                    pass = new String(Base64.getDecoder().decode(sourceConfig.getPass()));
                }
                Connection connection = getConnection(sourceConfig.getDsn(), sourceConfig.getUser(), pass);
                Map<Object, Connection> dataSourceMap = DataSourceMap.getInstance();
                dataSourceMap.put(sourceId, connection);
            }
            catch (SQLException throwables) {
                throwables.printStackTrace();
                //错误信息录入
                if (taskTypeEnum.READING_SOURCE_TASK.getCode().equals(taskType)) {
                    taskInfoService.updateReadingSourceTask(mainTaskId, null, sourceId,
                            taskStatusEnum.FAIL.getCode(), throwables.toString());
                }
                if (taskTypeEnum.CREATE_TABLE_TASK.getCode().equals(taskType)) {
                    taskInfoService.updateCreateTableTask(mainTaskId, null, sourceId,
                            taskStatusEnum.FAIL.getCode(), throwables.toString());
                }
            }
        }
    }

    @Override
    public SourceConfig getSourceConfig(String sourceId)
    {
        return sourceConfigMapper.selectById(sourceId);
    }

    @Override
    public void delDataSource(String sourceId)
    {
        Map<Object, Connection> dataSourceMap = DataSourceMap.getInstance();
        dataSourceMap.remove(sourceId);
    }

    /**
     * 数据库连接
     *
     * @param url 数据库连接地址
     * @param user 用户名
     * @param pwd 密码
     * @return Connection
     * @throws SQLException Exception
     */
    private static synchronized Connection getConnection(String url, String user, String pwd)
            throws SQLException
    {
        //判断数据源类型clickhouse 需要单独应用驱动类
        String str = "clickhouse";
        if (url.startsWith(str)) {
            try {
                Class.forName("ru.yandex.clickhouse.ClickHouseDriver");
            }
            catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return DriverManager.getConnection(url, user, pwd);
    }
}
