package com.wgzhao.addax.cli.mapper;

import com.wgzhao.addax.cli.pojo.SourceConfig;

public interface SourceConfigMapper {
    int deleteByPrimaryKey(String id);

    int insert(SourceConfig record);

    int insertSelective(SourceConfig record);

    SourceConfig selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SourceConfig record);

    int updateByPrimaryKey(SourceConfig record);

    SourceConfig selectById(String id);
}