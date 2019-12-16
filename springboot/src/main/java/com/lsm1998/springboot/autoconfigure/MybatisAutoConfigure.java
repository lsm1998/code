package com.lsm1998.springboot.autoconfigure;

import com.lsm1998.ibatis.session.MySqlSessionFactory;
import com.lsm1998.ibatis.session.MySqlSessionFactoryBuilder;

import java.util.Map;
import java.util.Properties;

/**
 * @作者：刘时明
 * @时间：19-1-11-上午9:57
 * @说明：
 */
public class MybatisAutoConfigure
{
    private Map<String,Object> map;
    private Properties properties;

    public MybatisAutoConfigure(Map<String,Object> map, Properties properties)
    {
        System.out.println("mybatis自动配置开始");
        this.map = map;
        this.properties = properties;
        auto();
        System.out.println("mybatis自动配置完成");
    }

    private void auto()
    {
        // 初始化Mybatis
        MySqlSessionFactory sessionFactory = new MySqlSessionFactoryBuilder().build(properties, true);
        map.put("com.lsm1998.ibatis.session.MyDefaultSqlSessionFactory", sessionFactory);
    }
}
