package com.wgzhao.addax.cli.server.impl;

import com.wgzhao.addax.cli.enums.taskStatusEnum;
import com.wgzhao.addax.cli.mapper.TableInfoMapper;
import com.wgzhao.addax.cli.mapper.TypeInfoMapper;
import com.wgzhao.addax.cli.pojo.SubTaskInfo;
import com.wgzhao.addax.cli.pojo.TableInfo;
import com.wgzhao.addax.cli.pojo.TypeInfo;
import com.wgzhao.addax.cli.server.TaskInfoService;

import java.sql.Connection;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.CountDownLatch;

/**
 * @author yangkai
 */
public class ThreadColInfoStorageTask
        implements Runnable
{

    private CountDownLatch countDownLatch;

    private Connection connection;

    private SubTaskInfo subTaskInfo;

    private Integer dtype;

    private final TaskInfoService taskInfoService;

    private final TableInfoMapper tableInfoMapper;

    private final TypeInfoMapper typeInfoMapper;

    ThreadColInfoStorageTask(Connection connection, SubTaskInfo subTaskInfo, TaskInfoService taskInfoService,
            TableInfoMapper tableInfoMapper,TypeInfoMapper typeInfoMapper,Integer dtype, CountDownLatch countDownLatch)
    {
        this.connection = connection;
        this.subTaskInfo = subTaskInfo;
        this.taskInfoService = taskInfoService;
        this.tableInfoMapper = tableInfoMapper;
        this.typeInfoMapper = typeInfoMapper;
        this.dtype = dtype;
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run()
    {
        Statement statement = null;
        try {
            //获取原表信息
            String sql = String.format("select * from %s where 1=2", subTaskInfo.getSourceDb() + "." + subTaskInfo.getSourceTbl());
            statement = connection.createStatement();
            ResultSetMetaData rsd = statement.executeQuery(sql).getMetaData();
            for (int i = 0; i < rsd.getColumnCount(); i++) {
                //设置源表信息
                TableInfo tableInfo = new TableInfo();
                tableInfo.setSourceId(subTaskInfo.getSourceId());
                tableInfo.setDbName(subTaskInfo.getSourceDb());
                tableInfo.setTblName(subTaskInfo.getSourceTbl());
                tableInfo.setColName(rsd.getColumnName(i + 1));
                tableInfo.setColType(rsd.getColumnTypeName(i + 1));
                tableInfo.setColLength(rsd.getPrecision(i + 1));
                tableInfo.setColPrecision(rsd.getScale(i + 1));
                tableInfo.setColPos(i + 1);
                //设置目标表信息
                TableInfo targetTableInfo = new TableInfo();
                targetTableInfo.setSourceId(subTaskInfo.getTargetId());
                targetTableInfo.setDbName(subTaskInfo.getTargetDb());
                targetTableInfo.setTblName(subTaskInfo.getTargetTbl());
                targetTableInfo.setColName(rsd.getColumnName(i + 1));
                //字段类型需要转换
                int cokType = rsd.getColumnType(i + 1);
                TypeInfo typeInfo = typeInfoMapper.selectTypeInfo(dtype, String.valueOf(cokType));
                String colType = "";
                if (typeInfo != null) {
                    colType = typeInfo.getColType();
                }
                else {
                    taskInfoService.updateTargetMappingTask( subTaskInfo.getId(), taskStatusEnum.FAIL.getCode(), "目标表字段类型映射失败");
                }
                targetTableInfo.setColType(colType);
                targetTableInfo.setColLength(rsd.getPrecision(i + 1));
                targetTableInfo.setColPrecision(rsd.getScale(i + 1));
                targetTableInfo.setColPos(i + 1);
                tableInfoMapper.insert(tableInfo);
                tableInfoMapper.insert(targetTableInfo);
                taskInfoService.updateReadingSourceTask(null, subTaskInfo.getId(), null, taskStatusEnum.SUCCESS.getCode(), null);
            }
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
            taskInfoService.updateReadingSourceTask(null, subTaskInfo.getId(), null, taskStatusEnum.FAIL.getCode(), throwables.toString());
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
        this.dtype = null;
    }
}
