package com.wgzhao.addax.cli.mapper;

import com.wgzhao.addax.cli.pojo.TaskInfo;

public interface TaskInfoMapper {
    int insert(TaskInfo record);

    int insertSelective(TaskInfo record);

    int update(String id);
}