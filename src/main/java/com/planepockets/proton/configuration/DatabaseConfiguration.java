package com.planepockets.proton.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
@PropertySource("classpath:proton.properties")
public class DatabaseConfiguration {

    @Value("${mysql.username}")
    private String mysqlUsername;

    @Value("${mysql.password}")
    private String mysqlPassword;

    @Value("${mysql.url}")
    private String mysqlUrl;

    @Value("${mysql.driver.classname}")
    private String mysqlDriverClassname;

    @Bean (name = "mysqlDatasource")
    public DataSource dm() {

        DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName(mysqlDriverClassname);
        dataSourceBuilder.password(mysqlPassword);
        dataSourceBuilder.username(mysqlUsername);
        dataSourceBuilder.url(mysqlUrl);
        return dataSourceBuilder.build();
    }
}
