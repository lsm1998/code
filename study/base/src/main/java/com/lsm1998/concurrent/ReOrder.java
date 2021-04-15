package com.lsm1998.concurrent;

import org.junit.Test;

public class ReOrder
{
    private static int a = 0;

    @Test
    public void testReOrder() throws Exception
    {
        Thread t1 = new Thread(() ->
        {
            a = 1000;
            System.out.println("set a!");
        });

        Thread t2 = new Thread(() ->
        {
            while (a < 100)
            {
            }
            System.out.println("end a=" + a);
        });

        t2.start();
        Thread.sleep(100);
        t1.start();

        Thread.sleep(10 * 1000);
    }
}
