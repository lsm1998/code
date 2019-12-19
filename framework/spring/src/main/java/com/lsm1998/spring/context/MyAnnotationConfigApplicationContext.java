package com.lsm1998.spring.context;

import java.util.Properties;

/**
 * @作者：刘时明
 * @时间:2018/12/20-20:36
 * @说明：基于注解的ApplicationContext
 */
public class MyAnnotationConfigApplicationContext extends MyGenericApplicationContext
{
    public MyAnnotationConfigApplicationContext(Class<?> rootClazz, Properties properties)
    {
        loadIOC(rootClazz, properties);
    }

    private void loadIOC(Class<?> rootClazz, Properties properties)
    {
        MyGenericApplicationContext context = new MyGenericApplicationContext();
        context.loadApplicationContext(rootClazz,properties);
    }
}
