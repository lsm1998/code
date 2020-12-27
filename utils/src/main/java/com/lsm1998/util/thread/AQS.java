package com.lsm1998.util.thread;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;

public class AQS
{
    private static final VarHandle STATE;
    private static final VarHandle HEAD;
    private static final VarHandle TAIL;

    static
    {
        MethodHandles.Lookup l = MethodHandles.lookup();
        try
        {
            STATE = l.findVarHandle(AQS.class, "state", int.class);
            HEAD = l.findVarHandle(AQS.class, "head", Node.class);
            TAIL = l.findVarHandle(AQS.class, "tail", Node.class);
        } catch (NoSuchFieldException | IllegalAccessException e)
        {
            throw new ExceptionInInitializerError(e);
        }
    }

    public AQS()
    {

    }

    static final class Node
    {
        Node SHARED = new Node();
        Node EXCLUSIVE = null;

        static final int CANCELLED = 1;
        static final int SIGNAL = -1;
        static final int CONDITION = -2;
        static final int PROPAGATE = -3;

        volatile int waitStatus;

        volatile Node prev;
        volatile Node next;

        volatile Thread thread;

        volatile Node nextWaiter;

        final boolean isShared()
        {
            return nextWaiter == SHARED;
        }

        final Node predecessor()
        {
            Node p = prev;
            if (p == null)
                throw new NullPointerException();
            else
                return p;
        }

        Node()
        {
        }

        Node(Node nextWaiter)
        {
            this.nextWaiter = nextWaiter;
            THREAD.set(this, Thread.currentThread());
        }

        Node(int waitStatus)
        {
            WAITSTATUS.set(this, waitStatus);
            THREAD.set(this, Thread.currentThread());
        }

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
    }
}
