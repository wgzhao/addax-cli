package com.wgzhao.addax.cli;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@ComponentScan(basePackages = {"com.*"})
@MapperScan(basePackages = "com.wgzhao.addax.admin.mapper")
@SpringBootApplication
public class AddaxCliApplication {
    public static void main(String[] args) {
        SpringApplication.run(AddaxCliApplication.class, args);
    }
}
