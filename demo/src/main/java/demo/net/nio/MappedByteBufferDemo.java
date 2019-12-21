/*
 * 作者：刘时明
 * 时间：2019/12/20-23:15
 * 作用：
 */
package demo.net.nio;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * MappedByteBuffer可以将文件映射到内存(堆外内存)中，不需要系统拷贝
 */
public class MappedByteBufferDemo
{
    public static void main(String[] args) throws IOException
    {
        RandomAccessFile accessFile = new RandomAccessFile("www.txt", "rw");
        FileChannel fileChannel = accessFile.getChannel();
        // 指定模式为读写模式
        // 起始位置为0
        // 映射内存大小为9（字节）
        MappedByteBuffer mappedBuffer = fileChannel.map(FileChannel.MapMode.READ_WRITE, 0, 9);
        mappedBuffer.put("刘时明".getBytes());
//        mappedBuffer.put(0,(byte) '刘');
//        mappedBuffer.put(1,(byte) '时');
//        mappedBuffer.put(2,(byte) '明');
//        mappedBuffer.put(3,(byte) '啊');
//        mappedBuffer.put(4,(byte) '啊');
        fileChannel.close();
        accessFile.close();
    }
}
