package com.lsm1998.util.concurrent;

/**
 * @作者：刘时明
 * @时间：2019/6/18-11:08
 * @作用：
 */
public class Test
{
    public static void main(String[] args) throws Exception
    {
        MyLock lock = new MyReentrantLock();

        lock.lock();
        lock.lock();

        lock.unlock();
    }
}
