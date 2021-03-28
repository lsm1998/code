package com.lsm1998.io.buffer;

import org.junit.Test;

import java.nio.CharBuffer;

public class BufferDemo
{
    @Test
    public void testMyCharBuffer()
    {
        MyCharBuffer buffer = new MyCharBuffer();

        buffer.putChar('1');
        buffer.putChar('2');
        buffer.putChar('3');
        buffer.putChar('4');
        buffer.putChar('5');

        buffer.flip();
        for (int i = 0; i < 5; i++)
        {
            System.out.println(buffer.readChar());
        }

        buffer.flip();
        buffer.putChar('x');
        for (int i = 0; i < 100; i++)
        {
            buffer.putChar((char) i);
        }
        buffer.flip();
        System.out.println(buffer.readChar());
    }

    @Test
    public void testCharBuffer()
    {
        CharBuffer buffer = CharBuffer.allocate(10);
        buffer.put('1');
        buffer.put('2');
        buffer.put('3');
        buffer.put('4');
        buffer.put('5');

        buffer.flip();
        for (int i = 0; i < 5; i++)
        {
            System.out.println(buffer.get());
        }

        buffer.flip();
        buffer.put('x');
        System.out.println(buffer.get());
    }
}
