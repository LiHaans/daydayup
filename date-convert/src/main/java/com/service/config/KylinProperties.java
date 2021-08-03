package com.service.config;

import lombok.Data;

/**
 * @Auther: lihang
 * @Date: 2021-07-27 21:56
 * @Description:
 */
@Data
public class KylinProperties {

    private String name;

    private String type;

    private String url;

    private String username;

    private String password;

    private String driverClassName;

    private long maxWaitTime;

    private int poolSize;
}
