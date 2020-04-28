package com.lsm1998.io.files;

import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @program: code
 * @description:
 * @author: lsm
 * @create: 2020-04-10 09:31
 **/
public class CopyBigFile
{
    /**
     * 使用Java拷贝大文件
     *
     * HBuilderX.rar 343MB
     */
    String newFileName="C:\\Users\\Administrator\\Desktop\\HBuilderX-copy.rar";

    /**
     * BIO拷贝
     * @throws Exception
     */
    @Test
    public void test1() throws Exception
    {
        long start=System.currentTimeMillis();
        File file=new File("C:\\Users\\Administrator\\Desktop\\HBuilderX.rar");
        FileInputStream inputStream=new FileInputStream(file);
        byte[] bytes=new byte[(int) file.length()];
        FileOutputStream outputStream=new FileOutputStream(newFileName);
        inputStream.read(bytes);
        outputStream.write(bytes);
        long end=System.currentTimeMillis();
        // 7015 ms
        System.out.println((end-start));
    }

    /**
     * NIO零复制拷贝
     * @throws Exception
     */
    @Test
    public void test2() throws Exception
    {
        long start=System.currentTimeMillis();
        File file=new File("C:\\Users\\Administrator\\Desktop\\HBuilderX.rar");
        RandomAccessFile source = new RandomAccessFile(file, "r");
        FileChannel inChannel = source.getChannel();
        MappedByteBuffer readMapped = inChannel.map(FileChannel.MapMode.READ_ONLY, 0,  file.length());
        byte[] bytes=new byte[(int) file.length()];
        readMapped.get(bytes);
        RandomAccessFile target = new RandomAccessFile(newFileName, "rw");
        FileChannel outChannel = target.getChannel();
        MappedByteBuffer writeMapped = outChannel.map(FileChannel.MapMode.READ_WRITE, 0,  file.length());
        writeMapped.put(bytes);
        long end=System.currentTimeMillis();
        // 797 ms
        System.out.println((end-start));
    }
}
