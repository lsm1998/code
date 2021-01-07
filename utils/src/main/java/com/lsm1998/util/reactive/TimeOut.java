package com.lsm1998.util.reactive;

import java.util.concurrent.TimeUnit;

public class TimeOut
{
    public static void main(String[] args) throws InterruptedException
    {
        long start = System.currentTimeMillis();
        TimeUnit.SECONDS.sleep(1);
        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }
}
