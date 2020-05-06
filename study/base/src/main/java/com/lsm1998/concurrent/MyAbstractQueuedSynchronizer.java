/*
 * 作者：刘时明
 * 时间：2020/5/2-11:57
 * 作用：
 */
package com.lsm1998.concurrent;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;
import java.util.concurrent.locks.LockSupport;

public abstract class MyAbstractQueuedSynchronizer extends MyAbstractOwnableSynchronizer
{
    private static final VarHandle STATE;
    private static final VarHandle HEAD;
    private static final VarHandle TAIL;

    static
    {
        try
        {
            MethodHandles.Lookup l = MethodHandles.lookup();
            STATE = l.findVarHandle(MyAbstractQueuedSynchronizer.class, "state", int.class);
            HEAD = l.findVarHandle(MyAbstractQueuedSynchronizer.class, "head", Node.class);
            TAIL = l.findVarHandle(MyAbstractQueuedSynchronizer.class, "tail", Node.class);
        } catch (ReflectiveOperationException e)
        {
            throw new ExceptionInInitializerError(e);
        }
        // Reduce the risk of rare disastrous classloading in first call to
        // LockSupport.park: https://bugs.openjdk.java.net/browse/JDK-8074773
        Class<?> ensureLoaded = LockSupport.class;
    }

    protected MyAbstractQueuedSynchronizer()
    {
    }

    static final class Node
    {
        // 共享模式下的等待标记
        static final Node SHARED = new Node();
        // 独占模式下的等待标记
        static final Node EXCLUSIVE = null;

        // 线程已取消标识
        static final int CANCELLED = 1;
        // 续线程需要断开连接标识
        static final int SIGNAL = -1;
        // 线程等待条件标识
        static final int CONDITION = -2;
        // 无条件传播标识
        static final int PROPAGATE = -3;

        // 等待标识，上面3个中的一个
        volatile int waitStatus;

        // 前驱节点
        volatile Node prev;
        // 后继节点
        volatile Node next;
        // 使此节点排队的线程
        volatile Thread thread;
        // 下一个等待条件的节点
        Node nextWaiter;

        Node()
        {
        }

//        Node(Node nextWaiter) {
//            this.nextWaiter = nextWaiter;
//            THREAD.set(this, Thread.currentThread());
//        }

        /**
         * 是否共享模式
         *
         * @return
         */
        final boolean isShared()
        {
            return nextWaiter == SHARED;
        }

        /**
         * 返回上一个节点
         *
         * @return
         */
        final Node predecessor()
        {
            Node p = prev;
            if (p == null)
                throw new NullPointerException();
            else
                return p;
        }

        private static final VarHandle NEXT;
        private static final VarHandle PREV;
        private static final VarHandle THREAD;
        private static final VarHandle WAITSTATUS;

        static
        {
            try
            {
                MethodHandles.Lookup l = MethodHandles.lookup();
                NEXT = l.findVarHandle(Node.class, "next", Node.class);
                PREV = l.findVarHandle(Node.class, "prev", Node.class);
                THREAD = l.findVarHandle(Node.class, "thread", Thread.class);
                WAITSTATUS = l.findVarHandle(Node.class, "waitStatus", int.class);
            } catch (ReflectiveOperationException e)
            {
                throw new ExceptionInInitializerError(e);
            }
        }

        /**
         * @param expect
         * @param update
         * @return
         */
        final boolean compareAndSetWaitStatus(int expect, int update)
        {
            return WAITSTATUS.compareAndSet(this, expect, update);
        }

        final boolean compareAndSetNext(Node expect, Node update)
        {
            return NEXT.compareAndSet(this, expect, update);
        }

        final void setPrevRelaxed(Node p)
        {
            PREV.set(this, p);
        }
    }

    private transient volatile Node head;

    private transient volatile Node tail;

    private volatile int state;

    protected final int getState()
    {
        return state;
    }

    protected final void setState(int newState)
    {
        state = newState;
    }

    protected final boolean compareAndSetState(int expect, int update)
    {
        return STATE.compareAndSet(this, expect, update);
    }
}
