package com.lsm1998.util.thread;

import java.util.concurrent.*;

/**
 * @作者：刘时明
 * @时间：2019/6/18-11:08
 * @作用：
 */
public class Test implements Callable<String>
{
    @Override
    public String call() throws Exception
    {
        for (int i = 0; i < 100; i++)
        {
            Thread.sleep(10);
            System.out.println("调用");
        }
        return "Hello!";
    }

    public static void main(String[] args) throws Exception
    {
        Test t=new Test();
        FutureTask<String> task=new FutureTask<>(t);
        // new Thread(task).start();
        task.run();
        for (int i = 0; i < 100; i++)
        {
            System.out.println("123");
        }
        System.out.println(task.get());
    }
}
