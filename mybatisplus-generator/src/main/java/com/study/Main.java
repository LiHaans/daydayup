package com.golaxy;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Auther: lihang
 * @Date: 2021-06-16 14:51
 * @Description:
 */
@SpringBootApplication
@MapperScan("com.study.mapper")
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

}
