package com.lsm1998.util.concurrent;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class MyReentrantLock implements MyLock
{
    private final Sync sync;

    public MyReentrantLock()
    {
        sync = new NonfairSync();
    }

    public MyReentrantLock(boolean fair)
    {
        sync = fair ? new FairSync() : new NonfairSync();
    }

    abstract static class Sync extends MyAQS
    {
        final boolean nonfairTryAcquire(int acquires)
        {
            final Thread current = Thread.currentThread();
            int c = getState();
            if (c == 0)
            {
                if (compareAndSetState(0, acquires))
                {
                    setExclusiveOwnerThread(current);
                    return true;
                }
            } else if (current == getExclusiveOwnerThread())
            {
                int nextc = c + acquires;
                if (nextc < 0) // overflow
                    throw new Error("Maximum lock count exceeded");
                setState(nextc);
                return true;
            }
            return false;
        }

        protected final boolean tryRelease(int releases)
        {
            int c = getState() - releases;
            if (Thread.currentThread() != getExclusiveOwnerThread())
                throw new IllegalMonitorStateException();
            boolean free = false;
            if (c == 0)
            {
                free = true;
                setExclusiveOwnerThread(null);
            }
            setState(c);
            return free;
        }

        protected final boolean isHeldExclusively()
        {
            // While we must in general read state before owner,
            // we don't need to do so to check if current thread is owner
            return getExclusiveOwnerThread() == Thread.currentThread();
        }

        final MyAQS.ConditionObject newCondition()
        {
            return new MyAQS.ConditionObject();
        }

        final Thread getOwner()
        {
            return getState() == 0 ? null : getExclusiveOwnerThread();
        }

        final int getHoldCount()
        {
            return isHeldExclusively() ? getState() : 0;
        }

        final boolean isLocked()
        {
            return getState() != 0;
        }

        /**
         * Reconstitutes the instance from a stream (that is, deserializes it).
         */
        private void readObject(java.io.ObjectInputStream s)
                throws java.io.IOException, ClassNotFoundException
        {
            s.defaultReadObject();
            setState(0); // reset to unlocked state
        }
    }

    // 不公平的同步器
    static final class NonfairSync extends Sync
    {
        protected final boolean tryAcquire(int acquires)
        {
            return nonfairTryAcquire(acquires);
        }
    }

    // 公平的同步器
    static final class FairSync extends Sync
    {
        protected final boolean tryAcquire(int acquires)
        {
            final Thread current = Thread.currentThread();
            int c = getState();
            if (c == 0)
            {
                if (!hasQueuedPredecessors() &&
                        compareAndSetState(0, acquires))
                {
                    setExclusiveOwnerThread(current);
                    return true;
                }
            } else if (current == getExclusiveOwnerThread())
            {
                int nextc = c + acquires;
                if (nextc < 0)
                    throw new Error("Maximum lock count exceeded");
                setState(nextc);
                return true;
            }
            return false;
        }
    }

    @Override
    public void lock()
    {
        sync.acquire(1);
    }

    @Override
    public boolean tryLock()
    {
        return sync.nonfairTryAcquire(1);
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException
    {
        return sync.tryAcquireNanos(1, unit.toNanos(time));
    }

    @Override
    public void unlock()
    {
        sync.release(1);
    }

    @Override
    public void lockInterruptibly() throws InterruptedException
    {
        sync.acquireInterruptibly(1);
    }

    @Override
    public MyCondition newCondition()
    {
        return sync.newCondition();
    }
}
