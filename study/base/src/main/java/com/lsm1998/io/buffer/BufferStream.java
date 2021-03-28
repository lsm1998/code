package com.lsm1998.io.buffer;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Random;
import java.util.concurrent.Future;

public class BufferStream
{
    private final String fileName = "word.txt";

    private long startTime;

    @Before
    public void init() throws Exception
    {
        File file = new File(fileName);
        if (!file.exists())
        {
            BufferedWriter bw = new BufferedWriter(new FileWriter(fileName));
            Random r = new Random();
            for (int i = 0; i < 1000_000; i++)
            {
                bw.write((char) r.nextInt());
            }
        }
        startTime = System.currentTimeMillis();
    }

    @After
    public void destroy()
    {
        System.out.println("程序耗时=" + (System.currentTimeMillis() - startTime) + " ms");
    }

    @Test
    public void testNIOStream() throws Exception
    {
        FileChannel channel = new FileInputStream(fileName).getChannel();

        ByteBuffer buffer = ByteBuffer.allocate(8 * 1024);
        while (channel.read(buffer) != -1)
        {
            buffer.flip();
            System.out.println(new String(buffer.array()));
            buffer.clear();
        }
    }

    @Test
    public void testBIOStream() throws Exception
    {
        BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(fileName));
        byte[] bytes = new byte[8 * 1024];
        int len;
        while ((len = inputStream.read(bytes)) != -1)
        {
            System.out.println(new String(bytes, 0, len));
        }
    }

    @Test
    public void testAIOStream() throws Exception
    {
        var channel = AsynchronousFileChannel.open(Path.of(fileName), StandardOpenOption.READ);
        ByteBuffer buffer = ByteBuffer.allocate(8 * 1024);
        int position = 0;
        while (true)
        {
            Future<Integer> future = channel.read(buffer, position);
            Integer readNum = future.get();
            if (readNum == -1)
            {
                break;
            }
            buffer.flip();
            System.out.println(new String(buffer.array()));
            buffer.clear();
            position += 8 * 1024;
        }
    }
}
