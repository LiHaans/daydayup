package com.study.config;

import net.sf.jsqlparser.schema.Database;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * @Auther: lihang
 * @Date: 2021-06-23 21:44
 * @Description:
 */
@Configuration
public class BrowserSecurityConfig {

    @Resource
    private DataSource dataSource;

    @Bean
    public PersistentTokenRepository persistentTokenRepository(){
        JdbcTokenRepositoryImpl impl = new JdbcTokenRepositoryImpl();
        impl.setDataSource(dataSource);
        impl.setCreateTableOnStartup(false); // 第一次执行sql生成表
        return impl;
    }
}
