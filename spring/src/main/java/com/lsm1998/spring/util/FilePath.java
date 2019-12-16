/*
 * 作者：刘时明
 * 时间：2019/12/17-0:02
 * 作用：
 */
package com.lsm1998.spring.util;

import java.io.File;

public class FilePath
{
    public static final String DIR = File.separator;
    public static final String RESOURCES_PATH = "src" + DIR + "main" + DIR + "resources" + DIR;
    public static final String TEMPLATES_PATH = RESOURCES_PATH + "templates";
    public static final String STATIC_PATH = RESOURCES_PATH + "static";
}
