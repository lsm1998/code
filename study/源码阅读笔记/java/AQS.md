# AQS

### 环境

基于Java11

### 概念

AbstractQueuedSynchronizer简称AQS，抽象队列同步器

### 继承关系

AbstractQueuedSynchronizer继承了AbstractOwnableSynchronizer（抽象可拥有同步器，定义了当前拥有线程，提供了该线程的set/get方法 ）

### AQS基本操作实现

1. 抢锁；
    1. 锁未被占有，需要抢锁，使用CAS保证只有一个线程抢到；
    2. 锁已经被占有，需要判断占用锁的是否是自身，满足重入机制，否则加入等待队列；
2. 释放锁；
    1. State值减一，如果为0，则锁占有线程置空，唤醒等待队列中的一个线程（方式取决于锁公平机制）；
    2. 公平锁：线程lock抢锁的时候判断队列是否有线程等待，如果有则不参与竞争；
    3. 非公平锁：线程lock抢锁的时候不会判断队列，直接参与CAS竞争；
3. 入队；
    1. 入队判断队列是否为空，如果为空则创建2个节点，第一个节点是当前占用锁的线程，第2个才是自己；
    2. 队列存在节点，则尾部插入；
4. 出队；
    1. 正常情况从头部出队；
    2. 非正常情况从中间或尾部出队； 3.非正常情况如何产生：第二个节点抢锁失败，cancelAcquire(node)，把第二个节点的waitStatus改成1
5. 阻塞，park，LockSupport.park(this)；
6. 唤醒，unPark， LockSupport.unpark(s.thread)；

#### Lock实现原理，以ReentrantLock为例：

```
维护了一个内部类Sync，继承自AbstractQueuedSynchronizer，实现了AQS功能；
NonfairSync和FairSync是Sync的子类，分别实现公平锁和不公平锁；
所有的请求线程构成一个队列，当一个线程执行完毕（lock.unlock()）时会唤醒自己的后继节点；
```

### lock过程分析

1. lock方法首先通过tryAcquire尝试获取锁；
   <br>代码片段（公平为例）：
   ````
       /**
         * 尝试获取锁
         * @param acquires
         * @return 是否抢到锁
         */
        protected final boolean tryAcquire(int acquires)
        {
            final Thread current = Thread.currentThread();
            int c = getState();
            // 如果state为0则尝试抢锁
            if (c == 0)
            {
                // 公平锁会判断队列是否存在等待线程
                // 通过CAS尝试将state设置为1
                if (!hasQueuedPredecessors() &&
                        compareAndSetState(0, acquires))
                {
                    // 抢锁成功，将同步的当前所有者设置为自己
                    setExclusiveOwnerThread(current);
                    return true;
                }
            } else if (current == getExclusiveOwnerThread()) // 是否重入
            {
                int nextc = c + acquires;
                if (nextc < 0)
                    throw new Error("Maximum lock count exceeded");
                setState(nextc);
                return true;
            }
            return false;
        }
   ````
2. 如果未能获取，则addWaiter加入等待队列；
   <br>代码片段：
   ````
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
   ````
3. 通过自旋再次尝试获取锁，如果未能获取，则通过shouldParkAfterFailedAcquire方法返回来设置阻塞；
   <br>代码片段
   ````
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
   ````
4.
shouldParkAfterFailedAcquire方法判断上一个节点waitStatus是否大于0，如果是则阻塞自身并退出循环，如果不是则跳过CANCELLED的node找waitStatus大于0的节点，将其状态设置成SIGNAL，继续循环；
<br>代码片段
````
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
````
5. 如果发生Throwable异常，则通过cancelAcquire方法取消该node，将节点waitStatus设置为CANCELLED，对应线程置空；

### unLock过程分析

1. 通过tryRelease(int releases)释放锁，先校验调用线程释放锁占有者是否调用线程，如果state归0则置空锁占有者并返回true；
   <br>代码片段：
   ````
      /**
         * 释放锁
         * 
         * @param releases
         * @return 是否需要释放锁
         */
        protected final boolean tryRelease(int releases)
        {
            int c = getState() - releases;
            // 校验调用线程
            if (Thread.currentThread() != getExclusiveOwnerThread())
                throw new IllegalMonitorStateException();
            boolean free = false;
            // state归0需要释放锁
            if (c == 0)
            {
                free = true;
                setExclusiveOwnerThread(null);
            }
            setState(c);
            return free;
        }
   ````
2. 如果需要释放锁，且如果队列不为空，以及头节点waitStatus不为0，则调用unparkSuccessor唤醒队列头节点；
   <br>代码片段：
   ````
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
   ````

