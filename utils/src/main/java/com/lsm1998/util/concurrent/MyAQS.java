package com.lsm1998.util.concurrent;

import java.io.Serializable;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * 抄了一遍AbstractQueuedSynchronizer，1000+行（除去注释）实现，目前水平不允许，只能拿来主义
 */
public abstract class MyAQS extends MyAOS
{
    protected MyAQS()
    {
    }

    static final class Node
    {
        // 共享的
        static final Node SHARED = new Node();

        // 独占的
        static final Node EXCLUSIVE = null;

        // 表示该线程节点已释放（超时、中断），已取消的节点不会再阻塞
        static final int CANCELLED = 1;

        // 表示该线程的后续线程需要阻塞，即只要前置节点释放锁，就会通知标识为SIGNAL状态的后续节点的线程
        static final int SIGNAL = -1;

        // 表示该线程在condition队列中阻塞（Condition有使用）
        static final int CONDITION = -2;

        // 表示该线程以及后续线程进行无条件传播（CountDownLatch中有使用）共享模式下，PROPAGATE状态的线程处于可运行状态
        static final int PROPAGATE = -3;

        volatile int waitStatus;

        volatile Node prev;

        volatile Node next;

        volatile Thread thread;

        Node nextWaiter;

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

        /**
         * Constructor used by addConditionWaiter.
         */
        Node(int waitStatus)
        {
            WAITSTATUS.set(this, waitStatus);
            THREAD.set(this, Thread.currentThread());
        }

        /**
         * CASes waitStatus field.
         */
        final boolean compareAndSetWaitStatus(int expect, int update)
        {
            return WAITSTATUS.compareAndSet(this, expect, update);
        }

        /**
         * CASes next field.
         */
        final boolean compareAndSetNext(Node expect, Node update)
        {
            return NEXT.compareAndSet(this, expect, update);
        }

        final void setPrevRelaxed(Node p)
        {
            PREV.set(this, p);
        }

        // VarHandle mechanics
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

    static final long SPIN_FOR_TIMEOUT_THRESHOLD = 1000L;

    private Node enq(Node node)
    {
        for (; ; )
        {
            Node oldTail = tail;
            if (oldTail != null)
            {
                node.setPrevRelaxed(oldTail);
                if (compareAndSetTail(oldTail, node))
                {
                    oldTail.next = node;
                    return oldTail;
                }
            } else
            {
                initializeSyncQueue();
            }
        }
    }

    /**
     * 当前线程加入锁的等待队列
     * 注意：如果队列为空，则会把正在占用锁的节点也加入队列
     *
     * @param mode
     * @return
     */
    private Node addWaiter(Node mode)
    {
        Node node = new Node(mode);
        for (; ; )
        {
            Node oldTail = tail;
            // 判断队列是否为空
            if (oldTail != null)
            {
                // 加入队列尾部
                node.setPrevRelaxed(oldTail);
                // 通过CAS安全的把自己设置成队列尾
                if (compareAndSetTail(oldTail, node))
                {
                    oldTail.next = node;
                    return node;
                }
            } else
            {
                // 初始化队列，自己new一个Node代表正在占用锁的节点
                initializeSyncQueue();
            }
        }
    }

    private void setHead(Node node)
    {
        head = node;
        node.thread = null;
        node.prev = null;
    }

    /**
     * 唤醒节点
     *
     * @param node
     */
    private void unparkSuccessor(Node node)
    {
        int ws = node.waitStatus;
        // 如果节点是阻塞状态，设置为锁占用状态
        if (ws < 0)
            node.compareAndSetWaitStatus(ws, 0);
        Node s = node.next;
        // 如果唤醒节点下下一个节点为空，或者等待状态为已释放
        if (s == null || s.waitStatus > 0)
        {
            s = null;

            // 从尾节点开始，找waitStatus<=0的节点，但是不包括唤醒节点
            for (Node p = tail; p != node && p != null; p = p.prev)
                if (p.waitStatus <= 0)
                    s = p;
        }
        if (s != null)
        {
            // 唤醒节点
            LockSupport.unpark(s.thread);
        }
    }

    private void doReleaseShared()
    {
        for (; ; )
        {
            Node h = head;
            if (h != null && h != tail)
            {
                int ws = h.waitStatus;
                if (ws == Node.SIGNAL)
                {
                    if (!h.compareAndSetWaitStatus(Node.SIGNAL, 0))
                        continue;            // loop to recheck cases
                    unparkSuccessor(h);
                } else if (ws == 0 &&
                        !h.compareAndSetWaitStatus(0, Node.PROPAGATE))
                    continue;                // loop on failed CAS
            }
            if (h == head)                   // loop if head changed
                break;
        }
    }

    private void setHeadAndPropagate(Node node, int propagate)
    {
        Node h = head;
        setHead(node);
        if (propagate > 0 || h == null || h.waitStatus < 0 ||
                (h = head) == null || h.waitStatus < 0)
        {
            Node s = node.next;
            if (s == null || s.isShared())
                doReleaseShared();
        }
    }

    /**
     * 取消节点
     *
     * @param node
     */
    private void cancelAcquire(Node node)
    {
        if (node == null)
            return;
        // 该节点对应线程置空
        node.thread = null;

        // 找到一个离当前节点最近且waitStatus>0（已释放锁）的前驱节点
        Node pred = node.prev;
        while (pred.waitStatus > 0)
            node.prev = pred = pred.prev;

        Node predNext = pred.next;

        // 取消的节点waitStatus设置成CANCELLED
        node.waitStatus = Node.CANCELLED;

        // 如果当前节点是尾节点
        if (node == tail && compareAndSetTail(node, pred))
        {
            pred.compareAndSetNext(predNext, null);
        } else
        {
            int ws;
            if (pred != head &&
                    ((ws = pred.waitStatus) == Node.SIGNAL ||
                            (ws <= 0 && pred.compareAndSetWaitStatus(ws, Node.SIGNAL))) &&
                    pred.thread != null)
            {
                Node next = node.next;
                if (next != null && next.waitStatus <= 0)
                    pred.compareAndSetNext(predNext, next);
            } else
            {
                unparkSuccessor(node);
            }
            node.next = node;
        }
    }

    /**
     * 把有效前驱（不是CANCELLED的node）的状态设置为SIGNAL
     *
     * @param pred
     * @param node
     * @return 是否可以立即阻塞
     */
    private static boolean shouldParkAfterFailedAcquire(Node pred, Node node)
    {
        int ws = pred.waitStatus;
        // 如果原本就是SIGNAL，立即阻塞
        if (ws == Node.SIGNAL)
            return true;
        // waitStatus大于0即为CANCELLED的node
        if (ws > 0)
        {
            // 通过do while跳过CANCELLED的node
            do
            {
                node.prev = pred = pred.prev;
            } while (pred.waitStatus > 0);
            pred.next = node;
        } else
        {
            // 关键代码：有效前驱的状态设置为SIGNAL
            pred.compareAndSetWaitStatus(ws, Node.SIGNAL);
        }
        // 只有当上一个节点原本就是SIGNAL才会立即阻塞
        return false;
    }

    static void selfInterrupt()
    {
        Thread.currentThread().interrupt();
    }

    private final boolean parkAndCheckInterrupt()
    {
        LockSupport.park(this);
        return Thread.interrupted();
    }

    /**
     * 通过自旋，判断当前队列节点是否可以获取锁
     *
     * @param node
     * @param arg
     * @return 返回线程是否被挂起
     */
    final boolean acquireQueued(final Node node, int arg)
    {
        boolean interrupted = false;
        try
        {
            for (; ; )
            {
                // 获取上一个节点
                final Node p = node.predecessor();
                // 如果上一个节点是head，也就是正在占用锁的线程
                // 那么再次尝试获取锁
                if (p == head && tryAcquire(arg))
                {
                    // 再次尝试的结果为成功时需要把自己设置成头节点
                    setHead(node);
                    p.next = null; // help GC
                    // 获取到锁了，所以线程没有被挂起
                    return interrupted;
                }
                // 上一个节点不是head，或者再次尝试获取锁失败了
                // 通过这个函数把上一个有效节点设置成SIGNAL，返回是否立即阻塞
                // 没有立即阻塞会继续自旋
                if (shouldParkAfterFailedAcquire(p, node))
                {
                    // 设置阻塞
                    interrupted |= parkAndCheckInterrupt();
                }
            }
        } catch (Throwable t)
        {
            cancelAcquire(node);
            if (interrupted)
                selfInterrupt();
            throw t;
        }
    }

    private void doAcquireInterruptibly(int arg)
            throws InterruptedException
    {
        final Node node = addWaiter(Node.EXCLUSIVE);
        try
        {
            for (; ; )
            {
                final Node p = node.predecessor();
                if (p == head && tryAcquire(arg))
                {
                    setHead(node);
                    p.next = null; // help GC
                    return;
                }
                if (shouldParkAfterFailedAcquire(p, node) &&
                        parkAndCheckInterrupt())
                    throw new InterruptedException();
            }
        } catch (Throwable t)
        {
            cancelAcquire(node);
            throw t;
        }
    }

    private boolean doAcquireNanos(int arg, long nanosTimeout)
            throws InterruptedException
    {
        if (nanosTimeout <= 0L)
            return false;
        final long deadline = System.nanoTime() + nanosTimeout;
        final Node node = addWaiter(Node.EXCLUSIVE);
        try
        {
            for (; ; )
            {
                final Node p = node.predecessor();
                if (p == head && tryAcquire(arg))
                {
                    setHead(node);
                    p.next = null; // help GC
                    return true;
                }
                nanosTimeout = deadline - System.nanoTime();
                if (nanosTimeout <= 0L)
                {
                    cancelAcquire(node);
                    return false;
                }
                if (shouldParkAfterFailedAcquire(p, node) &&
                        nanosTimeout > SPIN_FOR_TIMEOUT_THRESHOLD)
                    LockSupport.parkNanos(this, nanosTimeout);
                if (Thread.interrupted())
                    throw new InterruptedException();
            }
        } catch (Throwable t)
        {
            cancelAcquire(node);
            throw t;
        }
    }

    private void doAcquireShared(int arg)
    {
        final Node node = addWaiter(Node.SHARED);
        boolean interrupted = false;
        try
        {
            for (; ; )
            {
                final Node p = node.predecessor();
                if (p == head)
                {
                    int r = tryAcquireShared(arg);
                    if (r >= 0)
                    {
                        setHeadAndPropagate(node, r);
                        p.next = null; // help GC
                        return;
                    }
                }
                if (shouldParkAfterFailedAcquire(p, node))
                    interrupted |= parkAndCheckInterrupt();
            }
        } catch (Throwable t)
        {
            cancelAcquire(node);
            throw t;
        } finally
        {
            if (interrupted)
                selfInterrupt();
        }
    }

    private void doAcquireSharedInterruptibly(int arg)
            throws InterruptedException
    {
        final Node node = addWaiter(Node.SHARED);
        try
        {
            for (; ; )
            {
                final Node p = node.predecessor();
                if (p == head)
                {
                    int r = tryAcquireShared(arg);
                    if (r >= 0)
                    {
                        setHeadAndPropagate(node, r);
                        p.next = null; // help GC
                        return;
                    }
                }
                if (shouldParkAfterFailedAcquire(p, node) &&
                        parkAndCheckInterrupt())
                    throw new InterruptedException();
            }
        } catch (Throwable t)
        {
            cancelAcquire(node);
            throw t;
        }
    }

    private boolean doAcquireSharedNanos(int arg, long nanosTimeout)
            throws InterruptedException
    {
        if (nanosTimeout <= 0L)
            return false;
        final long deadline = System.nanoTime() + nanosTimeout;
        final Node node = addWaiter(Node.SHARED);
        try
        {
            for (; ; )
            {
                final Node p = node.predecessor();
                if (p == head)
                {
                    int r = tryAcquireShared(arg);
                    if (r >= 0)
                    {
                        setHeadAndPropagate(node, r);
                        p.next = null; // help GC
                        return true;
                    }
                }
                nanosTimeout = deadline - System.nanoTime();
                if (nanosTimeout <= 0L)
                {
                    cancelAcquire(node);
                    return false;
                }
                if (shouldParkAfterFailedAcquire(p, node) &&
                        nanosTimeout > SPIN_FOR_TIMEOUT_THRESHOLD)
                    LockSupport.parkNanos(this, nanosTimeout);
                if (Thread.interrupted())
                    throw new InterruptedException();
            }
        } catch (Throwable t)
        {
            cancelAcquire(node);
            throw t;
        }
    }

    /**
     * 尝试再次通过CAS获取一次锁
     *
     * @param arg
     * @return
     */
    protected abstract boolean tryAcquire(int arg);

    protected boolean tryRelease(int arg)
    {
        throw new UnsupportedOperationException();
    }

    protected int tryAcquireShared(int arg)
    {
        throw new UnsupportedOperationException();
    }

    protected boolean tryReleaseShared(int arg)
    {
        throw new UnsupportedOperationException();
    }

    protected boolean isHeldExclusively()
    {
        throw new UnsupportedOperationException();
    }

    public final void acquire(int arg)
    {
        // tryAcquire返回是否拿到锁，如果没拿到，则执行acquireQueued
        if (!tryAcquire(arg) && acquireQueued(addWaiter(Node.EXCLUSIVE), arg))
        {
            // 没有获取到锁&&线程被挂起
            // 则设置中断
            selfInterrupt();
        }
    }

    public final void acquireInterruptibly(int arg)
            throws InterruptedException
    {
        if (Thread.interrupted())
            throw new InterruptedException();
        if (!tryAcquire(arg))
            doAcquireInterruptibly(arg);
    }

    public final boolean tryAcquireNanos(int arg, long nanosTimeout) throws InterruptedException
    {
        if (Thread.interrupted())
            throw new InterruptedException();
        return tryAcquire(arg) || doAcquireNanos(arg, nanosTimeout);
    }

    public final boolean release(int arg)
    {
        // 是否需要释放锁
        if (tryRelease(arg))
        {
            Node h = head;
            // 如果队列不为空，且头节点waitStatus不为0
            if (h != null && h.waitStatus != 0)
            {
                // 唤醒节点
                unparkSuccessor(h);
            }
            return true;
        }
        return false;
    }

    public final void acquireShared(int arg)
    {
        if (tryAcquireShared(arg) < 0)
            doAcquireShared(arg);
    }

    public final void acquireSharedInterruptibly(int arg)
            throws InterruptedException
    {
        if (Thread.interrupted())
            throw new InterruptedException();
        if (tryAcquireShared(arg) < 0)
            doAcquireSharedInterruptibly(arg);
    }

    public final boolean tryAcquireSharedNanos(int arg, long nanosTimeout)
            throws InterruptedException
    {
        if (Thread.interrupted())
            throw new InterruptedException();
        return tryAcquireShared(arg) >= 0 ||
                doAcquireSharedNanos(arg, nanosTimeout);
    }

    public final boolean releaseShared(int arg)
    {
        if (tryReleaseShared(arg))
        {
            doReleaseShared();
            return true;
        }
        return false;
    }

    public final boolean hasQueuedThreads()
    {
        for (Node p = tail, h = head; p != h && p != null; p = p.prev)
            if (p.waitStatus <= 0)
                return true;
        return false;
    }

    public final boolean hasContended()
    {
        return head != null;
    }

    public final Thread getFirstQueuedThread()
    {
        // handle only fast path, else relay
        return (head == tail) ? null : fullGetFirstQueuedThread();
    }

    private Thread fullGetFirstQueuedThread()
    {
        Node h, s;
        Thread st;
        if (((h = head) != null && (s = h.next) != null &&
                s.prev == head && (st = s.thread) != null) ||
                ((h = head) != null && (s = h.next) != null &&
                        s.prev == head && (st = s.thread) != null))
            return st;
        Thread firstThread = null;
        for (Node p = tail; p != null && p != head; p = p.prev)
        {
            Thread t = p.thread;
            if (t != null)
                firstThread = t;
        }
        return firstThread;
    }

    public final boolean isQueued(Thread thread)
    {
        if (thread == null)
            throw new NullPointerException();
        for (Node p = tail; p != null; p = p.prev)
            if (p.thread == thread)
                return true;
        return false;
    }

    final boolean apparentlyFirstQueuedIsExclusive()
    {
        Node h, s;
        return (h = head) != null &&
                (s = h.next) != null &&
                !s.isShared() &&
                s.thread != null;
    }

    /**
     * 是否存在等待的线程，且不是自身
     *
     * @return
     */
    public final boolean hasQueuedPredecessors()
    {
        Node h, s;
        // 队列不存在任何节点返回false
        if ((h = head) != null)
        {
            // 如果队列只有一个头节点 或者 头节点的下一个节点waitStatus大于0（已释放锁）
            if ((s = h.next) == null || s.waitStatus > 0)
            {
                s = null;
                // 从尾节点开始，找waitStatus<=0的节点，但是不包括头节点
                for (Node p = tail; p != h && p != null; p = p.prev)
                {
                    if (p.waitStatus <= 0)
                        s = p;
                }
            }
            // 判断是否自身锁重入
            if (s != null && s.thread != Thread.currentThread())
                return true;
        }
        return false;
    }

    public final int getQueueLength()
    {
        int n = 0;
        for (Node p = tail; p != null; p = p.prev)
        {
            if (p.thread != null)
                ++n;
        }
        return n;
    }

    public final Collection<Thread> getQueuedThreads()
    {
        ArrayList<Thread> list = new ArrayList<>();
        for (Node p = tail; p != null; p = p.prev)
        {
            Thread t = p.thread;
            if (t != null)
                list.add(t);
        }
        return list;
    }

    public final Collection<Thread> getExclusiveQueuedThreads()
    {
        ArrayList<Thread> list = new ArrayList<>();
        for (Node p = tail; p != null; p = p.prev)
        {
            if (!p.isShared())
            {
                Thread t = p.thread;
                if (t != null)
                    list.add(t);
            }
        }
        return list;
    }

    public final Collection<Thread> getSharedQueuedThreads()
    {
        ArrayList<Thread> list = new ArrayList<>();
        for (Node p = tail; p != null; p = p.prev)
        {
            if (p.isShared())
            {
                Thread t = p.thread;
                if (t != null)
                    list.add(t);
            }
        }
        return list;
    }

    public String toString()
    {
        return super.toString()
                + "[State = " + getState() + ", "
                + (hasQueuedThreads() ? "non" : "") + "empty queue]";
    }

    final boolean isOnSyncQueue(Node node)
    {
        if (node.waitStatus == Node.CONDITION || node.prev == null)
            return false;
        if (node.next != null) // If has successor, it must be on queue
            return true;
        return findNodeFromTail(node);
    }

    private boolean findNodeFromTail(Node node)
    {
        for (Node p = tail; ; )
        {
            if (p == node)
                return true;
            if (p == null)
                return false;
            p = p.prev;
        }
    }

    final boolean transferForSignal(Node node)
    {
        if (!node.compareAndSetWaitStatus(Node.CONDITION, 0))
            return false;
        Node p = enq(node);
        int ws = p.waitStatus;
        if (ws > 0 || !p.compareAndSetWaitStatus(ws, Node.SIGNAL))
            LockSupport.unpark(node.thread);
        return true;
    }

    final boolean transferAfterCancelledWait(Node node)
    {
        if (node.compareAndSetWaitStatus(Node.CONDITION, 0))
        {
            enq(node);
            return true;
        }
        while (!isOnSyncQueue(node))
            Thread.yield();
        return false;
    }

    final int fullyRelease(Node node)
    {
        try
        {
            int savedState = getState();
            if (release(savedState))
                return savedState;
            throw new IllegalMonitorStateException();
        } catch (Throwable t)
        {
            node.waitStatus = Node.CANCELLED;
            throw t;
        }
    }

    public final boolean owns(ConditionObject condition)
    {
        return condition.isOwnedBy(this);
    }

    public final boolean hasWaiters(ConditionObject condition)
    {
        if (!owns(condition))
            throw new IllegalArgumentException("Not owner");
        return condition.hasWaiters();
    }

    public final int getWaitQueueLength(ConditionObject condition)
    {
        if (!owns(condition))
            throw new IllegalArgumentException("Not owner");
        return condition.getWaitQueueLength();
    }

    public final Collection<Thread> getWaitingThreads(ConditionObject condition)
    {
        if (!owns(condition))
            throw new IllegalArgumentException("Not owner");
        return condition.getWaitingThreads();
    }

    public class ConditionObject implements MyCondition, Serializable
    {
        private transient Node firstWaiter;

        private transient Node lastWaiter;

        public ConditionObject()
        {
        }

        private Node addConditionWaiter()
        {
            if (!isHeldExclusively())
                throw new IllegalMonitorStateException();
            Node t = lastWaiter;
            // If lastWaiter is cancelled, clean out.
            if (t != null && t.waitStatus != Node.CONDITION)
            {
                unlinkCancelledWaiters();
                t = lastWaiter;
            }

            Node node = new Node(Node.CONDITION);

            if (t == null)
                firstWaiter = node;
            else
                t.nextWaiter = node;
            lastWaiter = node;
            return node;
        }

        private void doSignal(Node first)
        {
            do
            {
                if ((firstWaiter = first.nextWaiter) == null)
                    lastWaiter = null;
                first.nextWaiter = null;
            } while (!transferForSignal(first) &&
                    (first = firstWaiter) != null);
        }

        private void doSignalAll(Node first)
        {
            lastWaiter = firstWaiter = null;
            do
            {
                Node next = first.nextWaiter;
                first.nextWaiter = null;
                transferForSignal(first);
                first = next;
            } while (first != null);
        }

        private void unlinkCancelledWaiters()
        {
            Node t = firstWaiter;
            Node trail = null;
            while (t != null)
            {
                Node next = t.nextWaiter;
                if (t.waitStatus != Node.CONDITION)
                {
                    t.nextWaiter = null;
                    if (trail == null)
                        firstWaiter = next;
                    else
                        trail.nextWaiter = next;
                    if (next == null)
                        lastWaiter = trail;
                } else
                    trail = t;
                t = next;
            }
        }

        public final void signal()
        {
            if (!isHeldExclusively())
                throw new IllegalMonitorStateException();
            Node first = firstWaiter;
            if (first != null)
                doSignal(first);
        }

        public final void signalAll()
        {
            if (!isHeldExclusively())
                throw new IllegalMonitorStateException();
            Node first = firstWaiter;
            if (first != null)
                doSignalAll(first);
        }

        public final void awaitUninterruptibly()
        {
            Node node = addConditionWaiter();
            int savedState = fullyRelease(node);
            boolean interrupted = false;
            while (!isOnSyncQueue(node))
            {
                LockSupport.park(this);
                if (Thread.interrupted())
                    interrupted = true;
            }
            if (acquireQueued(node, savedState) || interrupted)
                selfInterrupt();
        }

        private static final int REINTERRUPT = 1;

        private static final int THROW_IE = -1;

        private int checkInterruptWhileWaiting(Node node)
        {
            return Thread.interrupted() ?
                    (transferAfterCancelledWait(node) ? THROW_IE : REINTERRUPT) :
                    0;
        }

        private void reportInterruptAfterWait(int interruptMode)
                throws InterruptedException
        {
            if (interruptMode == THROW_IE)
                throw new InterruptedException();
            else if (interruptMode == REINTERRUPT)
                selfInterrupt();
        }

        public final void await() throws InterruptedException
        {
            if (Thread.interrupted())
                throw new InterruptedException();
            Node node = addConditionWaiter();
            int savedState = fullyRelease(node);
            int interruptMode = 0;
            while (!isOnSyncQueue(node))
            {
                LockSupport.park(this);
                if ((interruptMode = checkInterruptWhileWaiting(node)) != 0)
                    break;
            }
            if (acquireQueued(node, savedState) && interruptMode != THROW_IE)
                interruptMode = REINTERRUPT;
            if (node.nextWaiter != null) // clean up if cancelled
                unlinkCancelledWaiters();
            if (interruptMode != 0)
                reportInterruptAfterWait(interruptMode);
        }

        public final long awaitNanos(long nanosTimeout)
                throws InterruptedException
        {
            if (Thread.interrupted())
                throw new InterruptedException();
            final long deadline = System.nanoTime() + nanosTimeout;
            long initialNanos = nanosTimeout;
            Node node = addConditionWaiter();
            int savedState = fullyRelease(node);
            int interruptMode = 0;
            while (!isOnSyncQueue(node))
            {
                if (nanosTimeout <= 0L)
                {
                    transferAfterCancelledWait(node);
                    break;
                }
                if (nanosTimeout > SPIN_FOR_TIMEOUT_THRESHOLD)
                    LockSupport.parkNanos(this, nanosTimeout);
                if ((interruptMode = checkInterruptWhileWaiting(node)) != 0)
                    break;
                nanosTimeout = deadline - System.nanoTime();
            }
            if (acquireQueued(node, savedState) && interruptMode != THROW_IE)
                interruptMode = REINTERRUPT;
            if (node.nextWaiter != null)
                unlinkCancelledWaiters();
            if (interruptMode != 0)
                reportInterruptAfterWait(interruptMode);
            long remaining = deadline - System.nanoTime(); // avoid overflow
            return (remaining <= initialNanos) ? remaining : Long.MIN_VALUE;
        }

        public final boolean awaitUntil(Date deadline)
                throws InterruptedException
        {
            long abstime = deadline.getTime();
            if (Thread.interrupted())
                throw new InterruptedException();
            Node node = addConditionWaiter();
            int savedState = fullyRelease(node);
            boolean timedout = false;
            int interruptMode = 0;
            while (!isOnSyncQueue(node))
            {
                if (System.currentTimeMillis() >= abstime)
                {
                    timedout = transferAfterCancelledWait(node);
                    break;
                }
                LockSupport.parkUntil(this, abstime);
                if ((interruptMode = checkInterruptWhileWaiting(node)) != 0)
                    break;
            }
            if (acquireQueued(node, savedState) && interruptMode != THROW_IE)
                interruptMode = REINTERRUPT;
            if (node.nextWaiter != null)
                unlinkCancelledWaiters();
            if (interruptMode != 0)
                reportInterruptAfterWait(interruptMode);
            return !timedout;
        }

        public final boolean await(long time, TimeUnit unit)
                throws InterruptedException
        {
            long nanosTimeout = unit.toNanos(time);
            if (Thread.interrupted())
                throw new InterruptedException();
            final long deadline = System.nanoTime() + nanosTimeout;
            Node node = addConditionWaiter();
            int savedState = fullyRelease(node);
            boolean timedout = false;
            int interruptMode = 0;
            while (!isOnSyncQueue(node))
            {
                if (nanosTimeout <= 0L)
                {
                    timedout = transferAfterCancelledWait(node);
                    break;
                }
                if (nanosTimeout > SPIN_FOR_TIMEOUT_THRESHOLD)
                    LockSupport.parkNanos(this, nanosTimeout);
                if ((interruptMode = checkInterruptWhileWaiting(node)) != 0)
                    break;
                nanosTimeout = deadline - System.nanoTime();
            }
            if (acquireQueued(node, savedState) && interruptMode != THROW_IE)
                interruptMode = REINTERRUPT;
            if (node.nextWaiter != null)
                unlinkCancelledWaiters();
            if (interruptMode != 0)
                reportInterruptAfterWait(interruptMode);
            return !timedout;
        }

        final boolean isOwnedBy(MyAQS sync)
        {
            return sync == MyAQS.this;
        }

        protected final boolean hasWaiters()
        {
            if (!isHeldExclusively())
                throw new IllegalMonitorStateException();
            for (Node w = firstWaiter; w != null; w = w.nextWaiter)
            {
                if (w.waitStatus == Node.CONDITION)
                    return true;
            }
            return false;
        }

        protected final int getWaitQueueLength()
        {
            if (!isHeldExclusively())
                throw new IllegalMonitorStateException();
            int n = 0;
            for (Node w = firstWaiter; w != null; w = w.nextWaiter)
            {
                if (w.waitStatus == Node.CONDITION)
                    ++n;
            }
            return n;
        }

        protected final Collection<Thread> getWaitingThreads()
        {
            if (!isHeldExclusively())
                throw new IllegalMonitorStateException();
            ArrayList<Thread> list = new ArrayList<>();
            for (Node w = firstWaiter; w != null; w = w.nextWaiter)
            {
                if (w.waitStatus == Node.CONDITION)
                {
                    Thread t = w.thread;
                    if (t != null)
                        list.add(t);
                }
            }
            return list;
        }
    }

    // VarHandle mechanics
    private static final VarHandle STATE;
    private static final VarHandle HEAD;
    private static final VarHandle TAIL;

    static
    {
        try
        {
            MethodHandles.Lookup l = MethodHandles.lookup();
            STATE = l.findVarHandle(MyAQS.class, "state", int.class);
            HEAD = l.findVarHandle(MyAQS.class, "head", Node.class);
            TAIL = l.findVarHandle(MyAQS.class, "tail", Node.class);
        } catch (ReflectiveOperationException e)
        {
            throw new ExceptionInInitializerError(e);
        }
        Class<?> ensureLoaded = LockSupport.class;
    }

    /**
     * 初始化同步队列
     */
    private final void initializeSyncQueue()
    {
        Node h;
        if (HEAD.compareAndSet(this, null, (h = new Node())))
        {
            tail = h;
        }
    }

    private final boolean compareAndSetTail(Node expect, Node update)
    {
        return TAIL.compareAndSet(this, expect, update);
    }
}
