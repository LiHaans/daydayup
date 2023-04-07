package com.golaxy.distributedlock;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(exclude = DruidDataSourceAutoConfigure.class)
@MapperScan(basePackages = {"com.study.distributed.mapper"})
public class DistributedLockApplication {

    public static void main(String[] args) {
        SpringApplication.run(DistributedLockApplication.class, args);
    }

}
