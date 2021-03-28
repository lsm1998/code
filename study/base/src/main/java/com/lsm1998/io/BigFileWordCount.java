package com.lsm1998.io;

import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 面试题：
 * 有一个大文件，保存了大小固定的词汇，统计出出现次数最多的前五个词汇
 */
public class BigFileWordCount
{
    private final String fileName = "bigfile.temp";

    private final ExecutorService service = Executors.newFixedThreadPool(100);

    @Before
    public void bigFileInit() throws Exception
    {
        File file = new File(fileName);
        // 96 byte
        byte[] bytes = "秦时明月汉时关，万里长征人未还。但使龙城飞将在，不教胡马度阴山。".getBytes(StandardCharsets.UTF_8);
        System.out.println(bytes.length);
        // 文件写完后约4.5GB
        if (!file.exists())
        {
            BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(file));
            for (int i = 0; i < 50_000_000; i++)
            {
                outputStream.write(bytes);
            }
        }
    }

    @Test
    public void testBigFileWordCount() throws Exception
    {
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        char[] chars = new char[1024];
        int len;
        while ((len = br.read(chars)) > 0)
        {
            service.submit(new CountWordJob(chars, len));
        }
    }

    class CountWordJob extends Thread
    {
        public CountWordJob(char[] buf, int len)
        {

        }
    }
}
