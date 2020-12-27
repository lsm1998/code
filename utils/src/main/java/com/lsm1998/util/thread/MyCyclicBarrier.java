package com.lsm1998.util.thread;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class MyCyclicBarrier
{
    private final Runnable runnable;

    private int count;

    private final int defCount;

    private final ReentrantLock lock;

    private final Condition condition;

    public MyCyclicBarrier(int count, Runnable runnable)
    {
        this.count = count;
        this.defCount = count;
        this.runnable = runnable;
        this.lock = new ReentrantLock();
        this.condition = this.lock.newCondition();
    }

    public void await() throws InterruptedException, BrokenBarrierException
    {
        try
        {
            this.lock.lock();
            if (this.count == 0)
            {
                this.reset();
                return;
            } else if (this.count == 1)
            {
                Thread t = new Thread(this.runnable);
                t.join();
                t.start();
                this.condition.signalAll();
            }
            this.count--;
            this.condition.await();
        } finally
        {
            this.lock.unlock();
        }
    }

    public void reset()
    {
        this.count = this.defCount;
    }
}
