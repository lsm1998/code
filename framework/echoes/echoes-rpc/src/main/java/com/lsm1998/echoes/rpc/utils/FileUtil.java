package com.lsm1998.echoes.rpc.utils;

import java.io.File;
import java.io.FileFilter;

/**
 * @program: code
 * @description:
 * @author: lsm
 * @create: 2020-04-01 18:43
 **/
public class FileUtil
{
    private static final String[] EMPTY_ARR=new String[]{};

    public static String[] getClassFile(String path)
    {
        File dir = new File(path);
        if (!dir.exists() || !dir.isDirectory()) {
            return EMPTY_ARR;
        }
        File[] dirFiles = dir.listFiles(file -> (file.isDirectory())|| (file.getName().endsWith(".class")));

        return EMPTY_ARR;
    }
}
