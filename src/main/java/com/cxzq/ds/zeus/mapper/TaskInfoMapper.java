package com.cxzq.ds.zeus.mapper;

import com.cxzq.ds.zeus.pojo.TaskInfo;

public interface TaskInfoMapper {
    int insert(TaskInfo record);

    int insertSelective(TaskInfo record);

    int update(String id);
}