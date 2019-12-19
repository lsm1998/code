/**
 * 作者：刘时明
 * 时间：2019/12/19-8:53
 * 作用：Buffer的使用
 */
package demo.net.nio;

import lombok.val;

import java.nio.IntBuffer;

public class BufferDemo
{
    public static void main(String[] args)
    {
        val intBuffer = IntBuffer.allocate(5);
        intBuffer.put(1);
        intBuffer.put(10);
        // flip读写模式切换，默认写模式
        intBuffer.flip();
        while (intBuffer.hasRemaining())
        {
            System.out.println(intBuffer.get());
        }
        intBuffer.flip();
        intBuffer.put(10);


    }
}
