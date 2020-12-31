package com.cxzq.ds.zeus;

import com.hundsun.jrescloud.common.boot.CloudApplication;
import com.hundsun.jrescloud.common.boot.CloudBootstrap;
import com.hundsun.jrescloud.db.core.configuration.EnableCloudDataSource;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author yangkai
 */
@CloudApplication
@EnableCloudDataSource
@EnableAsync
@ComponentScan(basePackages = {"com.*"})
@MapperScan(basePackages = "com.cxzq.ds.zeus.mapper")
public class ZeusData
{
    public static void main(String[] args) {
        CloudBootstrap.run(ZeusData.class, args);
    }
}
