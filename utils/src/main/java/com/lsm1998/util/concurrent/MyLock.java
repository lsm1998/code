package com.lsm1998.util.concurrent;

import java.util.concurrent.TimeUnit;

/**
 * 同步锁顶层接口
 */
public interface MyLock
{
    void lock();

    boolean tryLock();

    boolean tryLock(long time, TimeUnit unit) throws InterruptedException;

    void unlock();

    void lockInterruptibly() throws InterruptedException;

    MyCondition newCondition();
}
