/*
 * 作者：刘时明
 * 时间：2019/12/20-23:04
 * 作用：
 */
package demo.net.nio;

import java.nio.ByteBuffer;

public class ReadOnlyBufferDemo
{
    public static void main(String[] args)
    {
        ByteBuffer buffer = ByteBuffer.allocate(64);
        System.out.println(buffer.getClass());
        for (byte i = 0; i < 64; i++)
        {
            buffer.put(i);
        }
        buffer.flip();

        // 获取只读buffer
        ByteBuffer readOnlyBuffer = buffer.asReadOnlyBuffer();
        System.out.println(readOnlyBuffer.getClass());
        while (readOnlyBuffer.hasRemaining())
        {
            System.out.println(readOnlyBuffer.get());
        }
    }
}
