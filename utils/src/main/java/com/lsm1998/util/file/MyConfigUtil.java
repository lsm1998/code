package com.lsm1998.util.file;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

/**
 * @作者：刘时明
 * @时间：2019/6/16-13:41
 * @作用：
 */
public class MyConfigUtil
{
    /**
     * 获取配置文件
     *
     * @param fileName
     * @return
     */
    public static Properties getPropertiesByResources(String fileName)
    {
        Properties conf = new Properties();

        File file = MyFiles.getFileByResources(fileName);
        try
        {
            conf.load(new FileInputStream(file));
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return conf;
    }
}
