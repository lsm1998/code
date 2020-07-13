package com.lsm1998.data.config;

import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.annotation.Resource;
import javax.sql.DataSource;

@Configuration
@MapperScan("com.lsm1998.data.mapper")
public class MybatisPlusConfig
{
    /**
     * 数据源路由
     */
    @Resource(name = "myRoutingDataSource")
    private DataSource myRoutingDataSource;

    /**
     * 使用MyBatis Plus的sqlSessionFactory代替
     *
     * @return sqlSessionFactory
     * @throws Exception
     */
    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception
    {
        MybatisSqlSessionFactoryBean sqlSessionFactoryBean = new MybatisSqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(myRoutingDataSource);
        // mybatisConfiguration.setObjectWrapperFactory(new MapWrapperFactory());
        GlobalConfig config = new GlobalConfig();
        sqlSessionFactoryBean.setGlobalConfig(config);
        sqlSessionFactoryBean.setPlugins(new Interceptor[]{new PaginationInterceptor()});
        return sqlSessionFactoryBean.getObject();
    }

    /**
     * 事务配置
     *
     * @return 事务管理器
     */
    @Bean
    public DataSourceTransactionManager transactionManager()
    {
        DataSourceTransactionManager tx = new DataSourceTransactionManager();
        tx.setDataSource(myRoutingDataSource);
        return tx;
    }
}
