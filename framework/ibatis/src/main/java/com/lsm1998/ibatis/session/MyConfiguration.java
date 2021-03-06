package com.lsm1998.ibatis.session;

import java.util.Properties;

/**
 * @作者：刘时明
 * @时间：18-12-21-下午3:05
 * @说明：
 */
public class MyConfiguration
{
    private Properties properties;
    public String url;
    public String username;
    public String password;
    public String driver;
    private boolean update = false;

    public MyConfiguration(Properties properties)
    {
        this.properties = properties;
        // 1.验证配置文件的正确性
        validateProperties();

        // 2.加载驱动
        loadDriver();
    }

    private void validateProperties()
    {
        if (properties.containsKey("url"))
        {
            url = properties.getProperty("url");
        } else
        {
            System.err.println("配置文件中找不到：url属性");
        }
        if (properties.containsKey("username"))
        {
            username = properties.getProperty("username");
        } else
        {
            System.err.println("配置文件中找不到：username属性");
        }
        if (properties.containsKey("password"))
        {
            password = properties.getProperty("password");
        } else
        {
            System.err.println("配置文件中找不到：password属性");
        }
        if (properties.containsKey("driver"))
        {
            driver = properties.getProperty("driver");
        } else
        {
            System.err.println("配置文件中找不到：driver属性");
        }
    }

    private void loadDriver()
    {
        try
        {
            Class.forName(driver);
            System.out.println("驱动加载完毕");
        } catch (ClassNotFoundException e)
        {
            System.err.println("找不到驱动类：" + driver);
        }
    }

    public void setUpdate(boolean update)
    {
        this.update = update;
    }
}
