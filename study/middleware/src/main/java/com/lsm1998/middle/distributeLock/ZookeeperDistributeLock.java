/**
 * 作者：刘时明
 * 时间：2021/3/30
 */
package com.lsm1998.middle.distributeLock;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.CountDownLatch;

public class ZookeeperDistributeLock extends DistributeLock implements Watcher
{
    private ZooKeeper zk = null;

    private String WAIT_LOCK;//等待前一个锁，相对于CURRENT_LOCK，那么前一个节点就是WAIT_LOCK

    private String CURRENT_LOCK;//表示当前的锁

    /**
     * CountDownLatch：
     * 一个可以用来协调多个线程之间的同步,
     * 或者说起到线程之间的通信作用的工具类。
     * 它能够使一个线程在等待另外一些线程完成各自工作之后，再继续执行。使用一个计数器进行实现。
     * 计数器初始值为线程的数量。
     * 当每一个线程完成自己任务后，计数器的值就会减一。
     * 当计数器的值为0时，表示所有的线程都已经完成了任务，
     * 然后在CountDownLatch上等待的线程就可以恢复执行任务。
     */
    private CountDownLatch countDownLatch;

    // 根节点
    private final String rootLock;

    public ZookeeperDistributeLock()
    {
        this("localhost:2128","/lock_root",4000);
    }

    public ZookeeperDistributeLock(String connectString, String rootLock,int sessionTimeout)
    {
        this.rootLock = rootLock;
        try
        {
            // 创建连接
            // this表示当前对象，因为当前对象实现了Watcher接口
            zk = new ZooKeeper(connectString, sessionTimeout, this);
            // exists判断根节点是否存在，含有注册事件 。watch： false .因为不需要再注册对这个节点的事件。
            Stat stat = zk.exists(rootLock, false);
            if (stat == null)
            {
                // 创建根节点
                zk.create(rootLock, "0".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void lock()
    {
        if (this.tryLock())
        {
            //如果获得锁成功，那么就恭喜获得锁了
            System.out.println(Thread.currentThread().getName() + "----->" + CURRENT_LOCK + "-->获得锁成功");
            return;//终止程序， 否则的话一直阻塞，一直等到锁释放为止。
        }
        waitForLock(WAIT_LOCK);//没有获得锁，继续等待锁。阻塞
    }

    //等待锁
    private boolean waitForLock(String prev)
    {
        try
        {
            //监听当前节点的上一个节点
            Stat stat = zk.exists(prev, true);//因为它没有获得锁，所以就需要监听上一个节点的状态
            if (stat != null)
            {
                System.out.println(Thread.currentThread().getName() + "--->等待锁" + rootLock + "/" + prev + "释放");
                //每次减少1
                countDownLatch = new CountDownLatch(1);
                //它能够使一个线程在等待另外一些线程完成各自工作之后，再继续执行。
                //直到prv节点不存在的时候,countDownLatch才释放。
                //个人理解：   countDownLatch发令枪在锁释放的时候，执行down()方法，
                // 其他进程节点开始抢锁。否则一直在 await()等待阻塞阶段。
                countDownLatch.await();
                System.out.println(Thread.currentThread().getName() + "---->" + "获得锁成功");
                //下面跳到  处理监听事件 process（）的监听方法

            }
        } catch (KeeperException | InterruptedException e)
        {
            e.printStackTrace();
        }
        return false;
    }


    public void unlock()
    {
        System.out.println(Thread.currentThread().getName() + "---->释放锁" + CURRENT_LOCK);
        try
        {
            //把临时有序节点都删掉。
            zk.delete(CURRENT_LOCK, -1);
            CURRENT_LOCK = null;
            zk.close();
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        } catch (KeeperException e)
        {
            e.printStackTrace();
        }
    }


    // apache中核心jar提供的唯一接口方法。其他方法均是 java.util.concurrent，JDK自带的。
    // 处理监听事件
    public void process(WatchedEvent watchedEvent)
    {
        //存在这样一个监听
        if (countDownLatch != null)
        {
            //直到prv节点不存在的时候（比如删除）
            this.countDownLatch.countDown();
        }
    }

    // 首先创建根下面的子节点，如果有三个客户端调用这个方法，那么会创建三个有序临时节点
    public boolean tryLock()
    {
        try
        {
            // 创建临时有序节点，每个进程进来都会创建成功 ，这个CURRENT_LOCK指的就是对应进程创建的临时节点
            CURRENT_LOCK = zk.create(rootLock + "/", "0".getBytes(),
                    ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
            // 1.针对每个客户端，他所拿到的是每个客户端所创建的临时节点
            // Thread.currentThread().getName()表示当前线程名。
            System.out.println(Thread.currentThread().getName() + "---->" + CURRENT_LOCK + "尝试竞争锁");
            // 2.获取根节点下面的子节点 ；watch：false;解释： getChildren也是一个注册监听的方法，再监听就矛盾了
            List<String> childrens = zk.getChildren(rootLock, false);
            // 3.拿到之后进行排序，找最小节点
            SortedSet<String> sortedSet = new TreeSet<>();//定义一个
            for (String children : childrens)
            {
                sortedSet.add(rootLock + "/" + children);
            }
            // 4.sortedSet是树状结构，获得当前子节点中最小的“节点”。
            String firstNode = sortedSet.first();
            /**
             *  如果前面有节点，那么就判断前面是最小的；当前没有节点了，那么当前就是最小的
             *  headSet可操纵回去的方法。相当于后退。
             *  假如 子节点CURRENT_LOCK 是sequence_2 ， 结果会返回sequence_1；如果是CURRENCE_LOCK是sequence_1,
             *  那么结果就返回sequence_1。
             */
            SortedSet<String> lessThenMe = sortedSet.headSet(CURRENT_LOCK);
            if (CURRENT_LOCK.equals(firstNode))
            {
                //通过当前的节点（CURRENT_LOCK是指对应进程创建的节点）和子节点中最小的节点进行比较
                //如果相等，表示获得锁成功。
                return true;
            }
            if (!lessThenMe.isEmpty())
            {
                WAIT_LOCK = lessThenMe.last();//获得比当前节点更小的最后一个节点，设置给WAIT_LOCK
            }
        } catch (KeeperException | InterruptedException e)
        {
            e.printStackTrace();
        }
        return false;
    }
}
