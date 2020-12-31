package com.cxzq.ds.zeus.config;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yangkai
 *
 * 该类用于存储Statement信息
 * key值为数据源ID
 */
public class  DataSourceMap
{
    private static Map<Object, Connection> map;

    private DataSourceMap() {}

    public static synchronized Map<Object,Connection> getInstance()
    {
        if (map == null) {
            map = new HashMap<>();
        }
        return map;
    }
}
