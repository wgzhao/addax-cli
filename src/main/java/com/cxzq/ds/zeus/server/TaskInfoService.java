package com.cxzq.ds.zeus.server;

import com.cxzq.ds.zeus.pojo.SubTaskInfo;

import java.util.List;

/**
 * @author yangkai
 */
public interface TaskInfoService
{

    /**
     * 更新读源任务状态
     *
     * @param mainTaskId 主任务ID
     * @param taskId 子任务ID
     * @param sourceId 数据源ID
     * @param taskStatus 任务状态
     * @param massage 信息
     */
    void updateReadingSourceTask(String mainTaskId, String taskId, String sourceId, Integer taskStatus, String massage);

    /**
     * 更新建表任务
     *
     * @param taskId 子任务ID
     * @param taskStatus 任务状态
     * @param massage 信息
     */
    void updateTargetMappingTask(String taskId, Integer taskStatus, String massage);

    /**
     * 更新建表任务
     *
     * @param mainTaskId 主任务ID
     * @param taskId 子任务ID
     * @param sourceId 数据源ID
     * @param taskStatus 任务状态
     * @param massage 信息
     */
    void updateCreateTableTask(String mainTaskId, String taskId, String sourceId, Integer taskStatus, String massage);


    /**
     * 主任务ID查询未执行的读源任务(根据源ID分组)
     *
     * @param mainTaskId 主任务ID
     * @return list
     */
    List<SubTaskInfo> getGroupColInfoStorageTasks(String mainTaskId);

    /**
     * 主任务ID查询未执行的读源任务
     *
     * @param mainTaskId 主任务ID
     * @return list
     */
    List<SubTaskInfo> getColInfoStorageTasks(String mainTaskId);

    /**
     * 主任务ID查询需要建表并且源信息任务已完成的 (根据目标源ID分组)
     *
     * @param mainTaskId 主任务ID
     * @return list
     */
    List<SubTaskInfo> getGroupCreateTableTas(String mainTaskId);

    /**
     * 主任务ID查询需要建表并且源信息任务已完成的
     *
     * @param mainTaskId 主任务ID
     * @return list
     */
    List<SubTaskInfo> getCreateTableTas(String mainTaskId);

    /**
     * 更新主任务状态
     * @param mainTaskId 主任务ID
     */
    void updateMainTaskStatus(String mainTaskId);
}
