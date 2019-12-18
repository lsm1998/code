package com.oo.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableAsync;

import javax.sql.DataSource;

/**
 * 作者：刘时明
 * 日期：2018/9/25
 * 时间：18:05
 * 说明：Spring配置类
 */
@Configuration
@ComponentScan("com.oo")
@PropertySource("classpath:jdbc.properties")
@EnableAsync
public class Application
{
    /**
     * 阿里巴巴数据源
     * @param driver
     * @param url
     * @param username
     * @param password
     * @return DruidDataSource
     */
    @Bean
    public DruidDataSource getDataSource(
            @Value("${jdbcDriver}") String driver,
            @Value("${jdbcUrl}") String url,
            @Value("${jdbcUser}") String username,
            @Value("${jdbcPassword}") String password)
    {
        DruidDataSource ds = new DruidDataSource();
        ds.setDriverClassName(driver);
        ds.setUrl(url);
        ds.setUsername(username);
        ds.setPassword(password);
        return ds;
    }

    /**
     * 根据数据源产生SessionFactory
     * @param ds
     * @return SqlSessionFactoryBean
     */
    @Bean("sqlSessionFactory")
    public SqlSessionFactoryBean getFactory(@Autowired DataSource ds)
    {
        SqlSessionFactoryBean factory = new SqlSessionFactoryBean();
        factory.setDataSource(ds);
        return factory;
    }

    /**
     * 扫描mapper接口
     * @return MapperScannerConfigurer
     */
    @Bean
    public MapperScannerConfigurer getScanner()
    {
        MapperScannerConfigurer scanner = new MapperScannerConfigurer();
        scanner.setBasePackage("com.oo.mapper");
        scanner.setSqlSessionFactoryBeanName("sqlSessionFactory");
        return scanner;
    }
}
