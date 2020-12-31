package com.cxzq.ds.zeus.server.impl;

import com.cxzq.ds.zeus.enums.taskStatusEnum;
import com.cxzq.ds.zeus.mapper.TableInfoMapper;
import com.cxzq.ds.zeus.pojo.SubTaskInfo;
import com.cxzq.ds.zeus.pojo.TableInfo;
import com.cxzq.ds.zeus.server.TaskInfoService;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @author yangkai
 */
public class ThreadCreateTableTask
        implements Runnable
{

    private CountDownLatch countDownLatch;

    private Connection connection;

    private SubTaskInfo subTaskInfo;

    private final TaskInfoService taskInfoService;

    private final TableInfoMapper tableInfoMapper;

    ThreadCreateTableTask(Connection connection, SubTaskInfo subTaskInfo,
            TaskInfoService taskInfoService,TableInfoMapper tableInfoMapper,
            CountDownLatch countDownLatch)
    {
        this.connection = connection;
        this.subTaskInfo = subTaskInfo;
        this.taskInfoService = taskInfoService;
        this.tableInfoMapper = tableInfoMapper;
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run()
    {
        Statement statement = null;
        try {
            statement = connection.createStatement();
            String creatable = String.format("CREATE TABLE %s", subTaskInfo.getTargetDb() + "." + subTaskInfo.getTargetTbl());
            //sql拼接
            StringBuilder sql = new StringBuilder();
            TableInfo tableInfo = new TableInfo();
            tableInfo.setSourceId(subTaskInfo.getTargetId());
            tableInfo.setDbName(subTaskInfo.getTargetDb());
            tableInfo.setTblName(subTaskInfo.getTargetTbl());
            List<TableInfo> tableInfoList = tableInfoMapper.getTableInfos(tableInfo);
            for (TableInfo table : tableInfoList) {
                sql.append(table.getColName()).append(" ");
                //判断精度是否大于0
                if (table.getColPrecision() > 0) {
                    sql.append(table.getColType()).append(" (").append(table.getColLength()).append(",").append(table.getColPrecision()).append("),");
                }
                else {
                    sql.append(table.getColType()).append(" (").append(table.getColLength()).append("),");
                }
            }
            sql.deleteCharAt(sql.length() - 1);
            creatable += "(" + sql + ")";
            statement.executeUpdate(creatable);
            taskInfoService.updateCreateTableTask(null, subTaskInfo.getId(), null, taskStatusEnum.SUCCESS.getCode(), null);
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
            taskInfoService.updateCreateTableTask(null, subTaskInfo.getId(), null, taskStatusEnum.FAIL.getCode(), throwables.toString());
        }
        finally {
            if (statement != null) {
                try {
                    statement.close();
                }
                catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
            countDownLatch.countDown();
        }
        this.connection = null;
        this.subTaskInfo = null;
    }
}