package com.lih.ylgy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class YlgyApplication {

    public static void main(String[] args) {
        SpringApplication.run(YlgyApplication.class, args);
    }

}
