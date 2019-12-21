package com.lsm1998.springboot.autoconfigure;

import com.lsm1998.ibatis.session.MySqlSessionFactory;
import com.lsm1998.ibatis.session.MySqlSessionFactoryBuilder;
import com.lsm1998.spring.beans.MyAutoConfigure;
import com.lsm1998.spring.beans.factory.MyBeanFactory;
import com.lsm1998.spring.context.MyApplicationContext;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Properties;

/**
 * @作者：刘时明
 * @时间：19-1-11-上午9:57
 * @说明：
 */
@Slf4j
public class MybatisAutoConfigure implements MyAutoConfigure
{
    private Properties properties;

    public MybatisAutoConfigure(Properties properties)
    {
        log.info("mybatis自动配置开始");
        this.properties = properties;
        log.info("mybatis自动配置完成");
    }

    @Override
    public void auth(MyApplicationContext context)
    {
        // 初始化Mybatis
        MySqlSessionFactory sessionFactory = new MySqlSessionFactoryBuilder().build(properties, true);
        context.register("com.lsm1998.ibatis.session.MyDefaultSqlSessionFactory", sessionFactory);
    }
}
