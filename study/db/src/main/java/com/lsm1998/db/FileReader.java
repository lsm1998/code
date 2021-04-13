/**
 * 作者：刘时明
 * 时间：2021/4/13
 */
package com.lsm1998.db;

import com.lsm1998.util.file.MyFiles;
import org.junit.Test;

import java.io.File;

public class FileReader
{
    @Test
    public void testRead()
    {
        File file = MyFiles.getFileByResources("data/hello.txt");

        System.out.println(file.length());
    }
}
