package com.lsm1998.jvm;

import com.lsm1998.util.file.YmlUtil;

import java.util.Map;

/**
 * @作者：刘时明
 * @时间：2019/6/16-18:58
 * @作用：
 */
public class Test
{
    public static void main(String[] args)
    {
        Map<String,Object> map= YmlUtil.getResMap("conf.yml");

        System.out.println(map);
    }
}
