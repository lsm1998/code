/*
 * 作者：刘时明
 * 时间：2020/5/2-10:31
 * 作用：
 */
package com.lsm1998.concurrent;

import com.lsm1998.concurrent.aqs.CountDownLatchExample;
import com.lsm1998.concurrent.aqs.SemaphoreExample;

public class Test
{
    @org.junit.Test
    public void testLatch() throws Exception
    {
        CountDownLatchExample.study();
    }

    @org.junit.Test
    public void testSemaphore() throws Exception
    {
        SemaphoreExample.study();
    }
}
