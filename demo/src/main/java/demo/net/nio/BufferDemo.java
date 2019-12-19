/**
 * 作者：刘时明
 * 时间：2019/12/19-8:53
 * 作用：Buffer的使用
 */
package demo.net.nio;

import java.nio.IntBuffer;

/**
 * NIO的数据操作是基于Buffer的
 */
public class BufferDemo
{
    public static void main(String[] args)
    {
        /**
         * mark <= position <= limit <= capacity
         * mark        标记(默认等于-1)
         * position    当前位置
         * limit       读写极限(默认等于容量)
         * capacity    容量
         *
         * 修改模式：
         *  limit置为position
         *  position置为0
         */
        IntBuffer intBuffer = IntBuffer.allocate(5);
        intBuffer.put(1);
        intBuffer.put(10);
        // flip读写模式切换，默认写模式
        intBuffer.flip();
        /**
         * flip()源码
         *  this.limit = this.position;
         *  this.position = 0;
         *  this.mark = -1;
         */
        while (intBuffer.hasRemaining())
        {
            System.out.println(intBuffer.get());
        }
        intBuffer.flip();
        intBuffer.put(10);
    }
}
