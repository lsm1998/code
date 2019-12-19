/*
 * 作者：刘时明
 * 时间：2019/12/19-22:24
 * 作用：
 */
package demo.net.nio;

import com.lsm1998.util.file.MyFiles;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 通道是双向读写的
 */
public class ChannelDemo
{
    public static void main(String[] args) throws IOException
    {
        // writeFile();
        // readFile();
        // copyFile();
        copyBigFile();
        // MyFiles.copy("images/2.jpg","images/2-copy.jpg");
    }

    // 写文件
    private static void writeFile() throws IOException
    {
        String str = "hello,lsm!!!";
        FileOutputStream fos = new FileOutputStream("hello.txt", true);
        FileChannel channel = fos.getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        buffer.put(str.getBytes());
        buffer.flip();
        channel.write(buffer);
        channel.close();
        fos.close();
    }

    // 读文件
    private static void readFile() throws IOException
    {
        FileInputStream fis = new FileInputStream("hello.txt");
        FileChannel channel = fis.getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        channel.read(buffer);
        System.out.println(new String(buffer.array()));
        channel.close();
        fis.close();
    }

    // 拷贝文件
    private static void copyFile() throws IOException
    {
        FileInputStream fis = new FileInputStream("hello.txt");
        FileChannel readChannel = fis.getChannel();
        FileOutputStream fos = new FileOutputStream("hello-copy.txt");
        FileChannel writeChannel = fos.getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(5);
        while (true)
        {
            // buffer.clear();
            int len = readChannel.read(buffer);
            if (len == -1)
            {
                break;
            } else
            {
                buffer.flip();
                writeChannel.write(buffer);
                buffer.flip();
            }
        }
        writeChannel.close();
        readChannel.close();
        fos.close();
        fis.close();
    }

    // 拷贝图片
    private static void copyBigFile() throws IOException
    {
        FileInputStream fis = new FileInputStream("images/1.jpg");
        FileChannel readChannel = fis.getChannel();
        FileOutputStream fos = new FileOutputStream("images/1-copy.jpg");
        FileChannel writeChannel = fos.getChannel();
        // 最多1M
        //ByteBuffer buffer = ByteBuffer.allocate(1024 * 1024);
        //int len = readChannel.read(buffer);
        writeChannel.transferFrom(readChannel, 0, readChannel.size());
    }
}
