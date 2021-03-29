package com.lsm1998.io.buffer;

import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.NumberFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 面试题：
 * 有一个大文件，保存了大小固定的词汇，统计出出现次数最多的前五个词汇
 * <p>
 * 分批次写入map，最后合并
 */
public class BigFileWordCount
{
    private final String fileName = "红楼梦.txt";

    private final ConcurrentHashMap<Character, Integer> countMap = new ConcurrentHashMap<>();

    private final ExecutorService pool = Executors.newFixedThreadPool(100);

    private final Lock[] lockGroup = new ReentrantLock[10];

    private final NumberFormat percentInstance = NumberFormat.getPercentInstance();

    private final AtomicInteger completeCount;

    {
        completeCount = new AtomicInteger(0);
        percentInstance.setMaximumFractionDigits(4);
        for (int i = 0; i < lockGroup.length; i++)
        {
            lockGroup[i] = new ReentrantLock();
        }
    }

    private long fileLength;

    private long jobCount;

    private int maxCompleteCount = 0;

    @Before
    public void bigFileInit() throws Exception
    {
        File file = new File(fileName);
        if (!file.exists())
        {
            // 96 byte
            byte[] bytes = "秦时明月汉时关，万里长征人未还。但使龙城飞将在，不教胡马度阴山。".getBytes(StandardCharsets.UTF_8);
            var outputStream = new BufferedOutputStream(new FileOutputStream(file));
            for (int i = 0; i < 50_000_000; i++)
            {
                outputStream.write(bytes);
            }
        }
        fileLength = file.length();
    }

    @Test
    public void testBigFileWordCount()
    {
        int BUF_SIZE = 2 * 1024;
        jobCount = fileLength / BUF_SIZE;
        if (fileLength % BUF_SIZE != 0)
        {
            jobCount++;
        }
        try (var br = new BufferedInputStream(new FileInputStream(fileName)))
        {
            byte[] bytes = new byte[BUF_SIZE];
            while (true)
            {
                int len = br.read(bytes);
                pool.submit(new CountWordJob(new String(bytes, 0, len).toCharArray()));
                if (len < BUF_SIZE)
                {
                    break;
                }
            }
            pool.shutdown();
            while (!pool.isTerminated())
            {
                Thread.sleep(1000);
            }

            var queue = new PriorityQueue<Map.Entry<Character, Integer>>((o1, o2) -> o1.getValue() - o2.getValue());

            countMap.forEach((k, v) ->
            {
                if (queue.size() < 5)
                {
                    queue.add(Map.entry(k, v));
                } else
                {
                    if (v > queue.peek().getValue())
                    {
                        queue.add(Map.entry(k, v));
                        queue.poll();
                    }
                }
            });
            System.out.println(queue);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    class CountWordJob extends Thread
    {
        private final char[] buf;

        public CountWordJob(char[] buf)
        {
            this.buf = buf;
        }

        @Override
        public void run()
        {
            for (int i = 0; i < buf.length; i++)
            {
                Integer count = countMap.putIfAbsent(buf[i], 1);
                if (count != null)
                {
                    Lock lock = lockGroup[buf[i] % 10];
                    lock.lock();
                    count = countMap.get(buf[i]);
                    countMap.put(buf[i], ++count);
                    lock.unlock();
                }
            }
            jobLog(completeCount.addAndGet(1));
        }
    }

    private synchronized void jobLog(int completeCount)
    {
        if (completeCount > maxCompleteCount)
        {
            String step = percentInstance.format(completeCount / (double) jobCount);
            System.out.println("====完成进度：" + step + " ====");
            maxCompleteCount = completeCount;
        }
    }
}
