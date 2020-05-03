/*
 * 作者：刘时明
 * 时间：2020/4/29-23:07
 * 作用：
 */
package com.lsm1998.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class FileUtil
{
    public static int[][] getArrByFile(String path,int width,int height)
    {
        URL resource = FileUtil.class.getResource(path);
        File file=new File(resource.getPath());
        int[][] map=new int[width][height];
        try(FileReader reader=new FileReader(file);
            BufferedReader br=new BufferedReader(reader))
        {
            String line;
            int count=0;
            while ((line=br.readLine())!=null&&count<height)
            {
                for (int i = 0; i < line.length(); i++)
                {
                    map[count][i]=line.charAt(i)-48;
                }
                count++;
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return map;
    }
}
