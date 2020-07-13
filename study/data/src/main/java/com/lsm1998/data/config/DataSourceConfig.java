package com.lsm1998.data.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class DataSourceConfig
{
    /**
     * 主数据库
     *
     * @return DataSource
     */
    @Bean("masterDataSource")
    @ConfigurationProperties("spring.datasource.master")
    public DataSource masterDataSource()
    {
        return DataSourceBuilder.create().build();
    }

    /**
     * 从数据库
     *
     * @return DataSource
     */
    @Bean("slaveDataSource")
    @ConfigurationProperties("spring.datasource.slave1")
    public DataSource slaveDataSource()
    {
        return DataSourceBuilder.create().build();
    }

    /**
     * 数据源路由
     *
     * @param masterDataSource 主数据库
     * @param slaveDataSource  从数据库
     * @return DataSource
     */
    @Bean(name = "myRoutingDataSource")
    public DataSource myRoutingDataSource(@Qualifier("masterDataSource") DataSource masterDataSource,
                                          @Qualifier("slaveDataSource") DataSource slaveDataSource)
    {
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put(DBTypeEnum.MASTER, masterDataSource);
        targetDataSources.put(DBTypeEnum.SLAVE, slaveDataSource);
        MyRoutingDataSource myRoutingDataSource = new MyRoutingDataSource();
        myRoutingDataSource.setDefaultTargetDataSource(masterDataSource);
        myRoutingDataSource.setTargetDataSources(targetDataSources);
        return myRoutingDataSource;
    }
}
