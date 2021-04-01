package com.lsm1998.meta;

public class TimeUsageAspect implements Aspect
{
    @Override
    public void before()
    {
        System.out.println("before");
    }

    @Override
    public void after()
    {
        System.out.println("after");
    }
}
