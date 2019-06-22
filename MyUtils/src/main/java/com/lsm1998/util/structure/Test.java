package com.lsm1998.util.structure;

import com.lsm1998.util.reflect.ReflectUtil;

/**
 * @作者：刘时明
 * @时间：2019/6/21-23:59
 * @作用：
 */
public class Test
{
    private String hello;

    public Test(String hello)
    {
        this.hello = hello;
    }

    public static void main(String[] args)
    {
        ReflectUtil reflect = ReflectUtil.of(Test.class, "世界");
        reflect.call("say");
        reflect.set("hello", "大千世界");
        reflect.call("say");
        // ReflectUtil.of(Test.class, "世界").call("say");
    }

    public void say()
    {
        System.out.println("你好，" + hello);
    }
}
