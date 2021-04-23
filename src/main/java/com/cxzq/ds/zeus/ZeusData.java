package com.cxzq.ds.zeus;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author yangkai
 */
@SpringBootApplication
@EnableAsync
@MapperScan(basePackages = "com.cxzq.ds.zeus.mapper")
public class ZeusData
{
    public static void main(String[] args) {
        SpringApplication.run(ZeusData.class, args);
    }
}
