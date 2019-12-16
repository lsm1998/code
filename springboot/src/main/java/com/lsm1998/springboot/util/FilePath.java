package com.lsm1998.springboot.util;

import java.io.File;

/**
 * @作者：刘时明
 * @时间：19-1-10-下午2:08
 * @说明：
 */
public class FilePath
{
    public static final String DIR = File.separator;
    public static final String RESOURCES_PATH = "src" + DIR + "main" + DIR + "resources" + DIR;
    public static final String TEMPLATES_PATH = RESOURCES_PATH + "templates";
    public static final String STATIC_PATH = RESOURCES_PATH + "static";
}
