package com.wgzhao.addax.cli.server.impl;

import com.wgzhao.addax.cli.enums.taskStatusEnum;
import com.wgzhao.addax.cli.mapper.SubTaskInfoMapper;
import com.wgzhao.addax.cli.mapper.TaskInfoMapper;
import com.wgzhao.addax.cli.pojo.SubTaskInfo;
import com.wgzhao.addax.cli.server.TaskInfoService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author yangkai
 */
@Service
public class TaskInfoServiceImpl
        implements TaskInfoService
{
    @Autowired
    private SubTaskInfoMapper subTaskInfoMapper;

    @Autowired
    private TaskInfoMapper taskInfoMapper;

    @Override
    public void updateReadingSourceTask(String mainTaskId, String taskId, String sourceId, Integer taskStatus, String massage)
    {
        SubTaskInfo subTaskInfo = new SubTaskInfo();
        if (StringUtils.isNotBlank(taskId)) {
            subTaskInfo.setId(taskId);
        }
        if (StringUtils.isNotBlank(mainTaskId)) {
            subTaskInfo.setTaskId(mainTaskId);
        }
        subTaskInfo.setMtime(new Date());
        if (taskStatusEnum.FAIL.getCode().equals(taskStatus)) {
            if (StringUtils.isNotBlank(sourceId)) {
                subTaskInfo.setSourceId(sourceId);
            }
            subTaskInfo.setAddFieldStatus(taskStatus);
            subTaskInfo.setAddFieldReason("读取源表信息信息任务失败：" + massage);
        }
        else {
            subTaskInfo.setAddFieldStatus(taskStatus);
        }
        subTaskInfoMapper.update(subTaskInfo);
    }

    @Override
    public void updateTargetMappingTask(String taskId, Integer taskStatus, String massage)
    {
        SubTaskInfo subTaskInfo = new SubTaskInfo();
        subTaskInfo.setId(taskId);
        subTaskInfo.setMtime(new Date());
        subTaskInfo.setTargetMappingStatus(taskStatus);
        subTaskInfo.setTargetMappingReason("字段类型映射失败：" + massage);
        subTaskInfoMapper.update(subTaskInfo);
    }

    @Override
    public void updateCreateTableTask(String mainTaskId, String taskId, String sourceId, Integer taskStatus, String massage)
    {
        SubTaskInfo subTaskInfo = new SubTaskInfo();
        if (StringUtils.isNotBlank(taskId)) {
            subTaskInfo.setId(taskId);
        }
        if (StringUtils.isNotBlank(mainTaskId)) {
            subTaskInfo.setTaskId(mainTaskId);
        }
        subTaskInfo.setMtime(new Date());
        if (taskStatusEnum.FAIL.getCode().equals(taskStatus)) {
            if (StringUtils.isNotBlank(sourceId)) {
                subTaskInfo.setTargetId(sourceId);
            }
            subTaskInfo.setAddTargetTblStatus(taskStatus);
            subTaskInfo.setAddTargetTblReason("建表任务失败：" + massage);
        }
        else {
            subTaskInfo.setAddTargetTblStatus(taskStatus);
        }
        subTaskInfoMapper.update(subTaskInfo);
    }

    @Override
    public List<SubTaskInfo> getGroupColInfoStorageTasks(String mainTaskId)
    {
        return subTaskInfoMapper.getGroupColInfoStorageTasks(mainTaskId);
    }

    @Override
    public List<SubTaskInfo> getColInfoStorageTasks(String mainTaskId)
    {
        return subTaskInfoMapper.getColInfoStorageTasks(mainTaskId);
    }

    @Override
    public List<SubTaskInfo> getGroupCreateTableTas(String mainTaskId)
    {
        return subTaskInfoMapper.getGroupCreateTableTas(mainTaskId);
    }

    @Override
    public List<SubTaskInfo> getCreateTableTas(String mainTaskId)
    {
        return subTaskInfoMapper.getCreateTableTas(mainTaskId);
    }

    @Override
    public void updateMainTaskStatus(String mainTaskId)
    {
        taskInfoMapper.update(mainTaskId);
    }
}
