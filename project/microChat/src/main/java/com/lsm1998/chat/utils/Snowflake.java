/**
 * 作者：刘时明
 * 时间：2019/11/2-16:31
 * 作用：
 */
package com.lsm1998.chat.utils;

import java.util.concurrent.atomic.AtomicLong;

public class Snowflake
{
    /**
     * 每一部分所占位数
     */
    private final long dataCenterIdBits = 5L;
    private final long workerIdBits = 5L;
    private final long sequenceBits = 12L;

    /**
     * 向左的位移
     */
    private final long timestampShift = sequenceBits + dataCenterIdBits + workerIdBits;
    private final long dataCenterIdShift = sequenceBits + workerIdBits;
    private final long workerIdShift = sequenceBits;

    /**
     * 起始时间戳，初始化后不可修改
     */
    private final long epoch = 1451606400000L; // 2016-01-01

    /**
     * 数据中心编码，初始化后不可修改
     * 最大值: 2^5-1 取值范围: [0,31]
     */
    private final long dataCenterId;

    // 机器或进程编码，最大值: 2^5-1 取值范围: [0,31]
    private final long workerId;

    // 序列号，最大值: 2^12-1 取值范围: [0,4095]
    private long sequence = 0L;

    // 上次执行生成ID方法的时间戳
    private long lastTimestamp = -1L;

    private final long maxDataCenterId = -1L ^ (-1L << dataCenterIdBits);
    private final long maxWorkerId = -1L ^ (-1L << workerIdBits);
    private final long maxSequence = -1L ^ (-1L << sequenceBits);

    /**
     * 生成序列号
     */
    public synchronized long nextId()
    {
        long currTimestamp = timestampGen();
        if (currTimestamp < lastTimestamp)
        {
            throw new IllegalStateException(
                    String.format("Clock moved backwards. Refusing to generate id for %d milliseconds",
                            lastTimestamp - currTimestamp));
        }
        if (currTimestamp == lastTimestamp)
        {
            sequence = (sequence + 1) & maxSequence;
            if (sequence == 0)
            {
                // 毫秒内超过了，阻塞到下一毫秒
                currTimestamp = waitNextMillis(currTimestamp);
            }
        } else
        {
            sequence = 0L;
        }
        lastTimestamp = currTimestamp;
        return ((currTimestamp - epoch) << timestampShift) |
                (dataCenterId << dataCenterIdShift) |
                (workerId << workerIdShift) |
                sequence;
    }

    public Snowflake(long dataCenterId, long workerId)
    {
        if (dataCenterId > maxDataCenterId || dataCenterId < 0)
        {
            throw new IllegalArgumentException(
                    String.format("datacenter Id can't be greater than %d or less than 0", maxDataCenterId));
        }
        if (workerId > maxWorkerId || workerId < 0)
        {
            throw new IllegalArgumentException(
                    String.format("worker Id can't be greater than %d or less than 0", maxWorkerId));
        }
        this.dataCenterId = dataCenterId;
        this.workerId = workerId;
    }

    /**
     * 追踪调用 waitNextMillis 方法的次数
     */
    private final AtomicLong waitCount = new AtomicLong(0);

    public long getWaitCount()
    {
        return waitCount.get();
    }

    /**
     * 循环阻塞直到下一秒
     */
    protected long waitNextMillis(long currTimestamp)
    {
        waitCount.incrementAndGet();
        while (currTimestamp <= lastTimestamp)
        {
            currTimestamp = timestampGen();
        }
        return currTimestamp;
    }

    /**
     * 获取当前时间戳
     */
    public long timestampGen()
    {
        return System.currentTimeMillis();
    }
}
