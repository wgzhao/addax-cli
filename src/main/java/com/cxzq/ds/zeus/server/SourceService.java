package com.cxzq.ds.zeus.server;

import com.cxzq.ds.zeus.pojo.SourceConfig;

/**
 * @author yangkai
 */
public interface SourceService
{
    /**
     * ID获取数据源信息并连接数据源
     *
     * @param sourceId 数据源ID
     * @param taskId 任务ID
     * @param mainTaskId 主任务ID
     * @param taskType 任务类型
     */
    void connectDataSource(String sourceId, String taskId, String mainTaskId, String taskType);

    /**
     * ID获取数据源信息
     *
     * @param sourceId 数据源ID
     * @return SourceConfig
     */
    SourceConfig getSourceConfig(String sourceId);

    /**
     * 删除jdbc连接信息
     * @param sourceId 数据源ID
     */
    void delDataSource(String sourceId);
}
