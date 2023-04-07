package com.golaxy;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;

/**
 * @Auther: lihang
 * @Date: 2021-06-22 21:37
 * @Description:
 */
@SpringBootApplication(exclude = ErrorMvcAutoConfiguration.class)
@MapperScan("com.study.mapper")
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}
