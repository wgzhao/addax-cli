package com.cxzq.ds.zeus.server;

/**
 * 任务接口
 *
 * @author yangkai
 */
public interface TaskService
{
    /**
     * 字段信息入库任务
     *
     * @param mainTaskId 主任务ID
     */
    void colInfoStorageTask(String mainTaskId);

    /**
     * 建表任务
     *
     * @param mainTaskId 主任务ID
     */
    void createTableTask(String mainTaskId);
}
