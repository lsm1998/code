# AQS

### 概念

AbstractQueuedSynchronizer简称AQS，抽象队列同步器

### 继承关系

AbstractQueuedSynchronizer继承了AbstractOwnableSynchronizer（抽象可拥有同步器，定义了当前拥有线程，提供了该线程的set/get方法 ）

### 自己动手实现AQS

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
    2. 非正常情况从中间或尾部出队；
    3.非正常情况如何产生：第二个节点抢锁失败，cancelAcquire(node)，把第二个节点的waitStatus改成1
5. 阻塞，park，LockSupport.park(this)；
6. 唤醒，unPark， LockSupport.unpark(s.thread)；


#### Lock实现原理，以ReentrantLock为例：
```
维护了一个内部类Sync，继承自AbstractQueuedSynchronizer，实现了AQS功能；
NonfairSync和FairSync是Sync的子类，分别实现公平锁和不公平锁；
所有的请求线程构成一个队列，当一个线程执行完毕（lock.unlock()）时会激活自己的后继节点；
```
