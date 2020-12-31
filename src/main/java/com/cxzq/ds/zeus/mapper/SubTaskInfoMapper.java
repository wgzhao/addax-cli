package com.cxzq.ds.zeus.mapper;

import com.cxzq.ds.zeus.pojo.SubTaskInfo;

import java.util.List;

public interface SubTaskInfoMapper {
    int deleteByPrimaryKey(String id);

    int insert(SubTaskInfo record);

    int insertSelective(SubTaskInfo record);

    SubTaskInfo selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SubTaskInfo record);

    int updateByPrimaryKey(SubTaskInfo record);

    int update(SubTaskInfo record);

    /**
     * 主任务ID查询未执行的读源任务(根据源ID分组)
     */
    List<SubTaskInfo> getGroupColInfoStorageTasks(String mainTaskId);

    /**
     * 主任务ID查询未执行的读源任务
     */
    List<SubTaskInfo> getColInfoStorageTasks(String mainTaskId);

    /**
     * 主任务ID查询需要建表并且源信息任务已完成的 (根据目标源ID分组)
     */
    List<SubTaskInfo> getGroupCreateTableTas(String mainTaskId);

    /**
     * 主任务ID查询需要建表并且源信息任务已完成的
     */
    List<SubTaskInfo> getCreateTableTas(String mainTaskId);



}