package com.lsm1998.io.buffer;

import org.junit.Test;

import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.concurrent.*;

public class WordCount
{
    final ForkJoinPool pool = ForkJoinPool.commonPool();

    private static HashMap<String, Integer> countByString(String str)
    {
        var map = new HashMap<String, Integer>();
        StringTokenizer tokenizer = new StringTokenizer(str);
        while (tokenizer.hasMoreTokens())
        {
            var word = tokenizer.nextToken();
            incKey(word, map, 1);
        }
        return map;
    }

    private static void incKey(String key, HashMap<String, Integer> map, Integer n)
    {
        if (map.containsKey(key))
        {
            map.put(key, map.get(key) + n);
        } else
        {
            map.put(key, n);
        }
    }


    static class CountTask implements Callable<HashMap<String, Integer>>
    {
        private final long start;
        private final long end;
        private final String fileName;

        public CountTask(String fileName, long start, long end)
        {
            this.start = start;
            this.end = end;
            this.fileName = fileName;

        }

        @Override
        public HashMap<String, Integer> call() throws Exception
        {
            var channel = new RandomAccessFile(this.fileName, "rw").getChannel();

            // [start, end] -> Memory
            // Device -> Kernel Space -> UserSpace(buffer) -> Thread
            var mbuf = channel.map(
                    FileChannel.MapMode.READ_ONLY,
                    this.start,
                    this.end - this.start
            );
            var str = StandardCharsets.US_ASCII.decode(mbuf).toString();
            return countByString(str);
        }
    }


    public void run(String fileName, long chunkSize) throws ExecutionException, InterruptedException
    {
        var file = new File(fileName);
        var fileSize = file.length();

        long position = 0;

        var startTime = System.currentTimeMillis();
        var tasks = new ArrayList<Future<HashMap<String, Integer>>>();
        while (position < fileSize)
        {
            var next = Math.min(position + chunkSize, fileSize);
            var task = new CountTask(fileName, position, next);
            position = next;
            var future = pool.submit(task);
            tasks.add(future);
        }
        System.out.format("split to %d tasks\n", tasks.size());

        var totalMap = new HashMap<String, Integer>();
        for (var future : tasks)
        {
            var map = future.get();
            for (var entry : map.entrySet())
            {
                incKey(entry.getKey(), totalMap, entry.getValue());
            }
        }

        System.out.println("time:" + (System.currentTimeMillis() - startTime) + "ms");
        System.out.println("total:" + totalMap.size());

        System.out.println(totalMap.get("ababb"));
    }

    @Test
    public void count() throws ExecutionException, InterruptedException
    {
        var counter = new WordCount();
        System.out.println("processors:" + Runtime.getRuntime().availableProcessors());
        counter.run("word", 1024 * 1024 * 20);
    }

    @Test
    public void compare_with_single() throws IOException
    {
        var in = new BufferedInputStream(new FileInputStream("word"));
        var buf = new byte[4 * 1024];
        var len = 0;
        var total = new HashMap<String, Integer>();
        var startTime = System.currentTimeMillis();
        while ((len = in.read(buf)) != -1)
        {
            var bytes = Arrays.copyOfRange(buf, 0, len);
            var str = new String(bytes);
            var hashMap = countByString(str);
            for (var entry : hashMap.entrySet())
            {
                var key = entry.getKey();
                incKey(key, total, entry.getValue());
            }
        }
        // 阿姆达定律
        // 120s -> 16core -> 120/16 = ? 
        //  P    NP

        System.out.println("time:" + (System.currentTimeMillis() - startTime) + "ms");
        System.out.println(total.get("ababb"));
        System.out.println(total.size());
    }
}
