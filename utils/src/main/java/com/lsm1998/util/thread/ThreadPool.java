package com.lsm1998.util.thread;

import java.util.concurrent.locks.ReentrantLock;

public class ThreadPool
{
    private static final int DEF_CAP = 10;

    private volatile boolean RUNNING;

    private final MyBlockingQueue<Runnable> taskQueue;

    private final Task[] tasks;

    public ThreadPool()
    {
        this(DEF_CAP);
    }

    public ThreadPool(int cap)
    {
        if (cap < 0)
        {
            cap = DEF_CAP;
        }
        taskQueue = new MyBlockingQueue<>(cap);
        tasks = new Task[cap];
        for (int i = 0; i < cap; i++)
        {
            tasks[i] = new Task();
            tasks[i].start();
        }
        this.RUNNING = true;
    }

    public void submit(Runnable runnable) throws InterruptedException
    {
        if (this.RUNNING)
        {
            this.taskQueue.push(runnable);
        } else
        {
            System.err.println("thread pool is stop!");
        }
    }

    public void shutdown()
    {
        this.RUNNING = false;
        for (Task t : tasks)
        {
            while (true)
            {
                if (!t.isInterrupted() && t.lock.tryLock())
                {
                    try
                    {
                        t.interrupt();
                        break;
                    } catch (SecurityException e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    class Task extends Thread
    {
        ReentrantLock lock;

        public Task()
        {
            lock = new ReentrantLock();
        }

        @Override
        public void run()
        {
            while (true)
            {
                try
                {
                    Runnable runnable = taskQueue.poll();
                    lock.lockInterruptibly();
                    System.out.println("拿到任务，开始执行！！！");
                    runnable.run();
                } catch (InterruptedException | IllegalMonitorStateException e)
                {
                    if (e instanceof IllegalMonitorStateException)
                    {
                        System.out.println("线程退出");
                    }
                    e.printStackTrace();
                } finally
                {
                    lock.unlock();
                }
            }
        }
    }
}
