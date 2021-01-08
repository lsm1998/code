package com.lsm1998.util.concurrent;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 实现阻塞队列
 */
public class MyBlockingQueue<T>
{
    private final int cap;

    private final LinkedList<T> list;

    private final ReentrantLock lock;

    private final Condition notEmpty;

    private final Condition notFull;

    public MyBlockingQueue(int cap)
    {
        this.cap = cap;
        this.list = new LinkedList<>();
        this.lock = new ReentrantLock();
        this.notEmpty = this.lock.newCondition();
        this.notFull = this.lock.newCondition();
    }

    public T poll() throws InterruptedException
    {
        T temp;
        try
        {
            lock.lockInterruptibly();
            while (list.size() == 0)
            {
                notEmpty.await();
            }
            temp = list.getFirst();
            list.removeFirst();
            notFull.signal();
        } finally
        {
            lock.unlock();
        }
        return temp;
    }

    public void push(T data) throws InterruptedException
    {
        try
        {
            lock.lockInterruptibly();
        } finally
        {
            while (list.size() == cap)
            {
                notFull.await();
            }
            list.addLast(data);
            notEmpty.signal();
            lock.unlock();
        }
    }
}
