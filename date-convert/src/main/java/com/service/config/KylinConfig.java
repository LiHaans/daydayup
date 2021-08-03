package com.service.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;


/**
 * @Auther: lihang
 * @Date: 2021-07-26 09:47
 * @Description:
 */


@Slf4j
@Configuration
public class KylinConfig {

    @Bean(name = "kylinProperties")
    @ConfigurationProperties(prefix = "kylin")
    public KylinProperties creatKylinProperties() {
        return new KylinProperties();
    }

    @Bean(name = "kylinDataSource")
    public DataSource KylinDataSource(@Qualifier("kylinProperties") KylinProperties kylinProperties) {
        log.info("-------------------- kylin init ---------------------");
        return new KylinDataSource(kylinProperties);
    }

    @Bean(name = "kylinTemplate")
    public JdbcTemplate prestoJdbcTemplate(@Qualifier("kylinDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

}


