package com.wgzhao.addax.cli.mapper;

import com.wgzhao.addax.cli.pojo.TableInfo;

import java.util.List;

public interface TableInfoMapper {
    int insert(TableInfo record);

    int insertSelective(TableInfo record);

    /**
     * 查询字段表
     * @param record 查询条件
     * @return list
     */
    List<TableInfo> getTableInfos(TableInfo record);
}