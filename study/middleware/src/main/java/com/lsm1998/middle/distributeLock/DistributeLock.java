/**
 * 作者：刘时明
 * 时间：2021/3/30
 */
package com.lsm1998.middle.distributeLock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * 分布式锁
 */
public abstract class DistributeLock implements Lock
{
    @Override
    public abstract void lock();

    @Override
    public abstract boolean tryLock();

    @Override
    public abstract void unlock();

    @Override
    public void lockInterruptibly() throws InterruptedException
    {
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException
    {
        return false;
    }

    @Override
    public Condition newCondition()
    {
        throw new RuntimeException("DistributeLock Not Supported newCondition!");
    }
}
