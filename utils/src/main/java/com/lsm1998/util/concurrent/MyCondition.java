package com.lsm1998.util.concurrent;

/**
 * 线程同步条件顶层接口
 */
public interface MyCondition
{
    void await() throws InterruptedException;

    void signal();

    void signalAll();
}
