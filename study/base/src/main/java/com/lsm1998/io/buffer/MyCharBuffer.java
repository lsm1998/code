package com.lsm1998.io.buffer;

import java.nio.BufferOverflowException;
import java.util.Arrays;

public class MyCharBuffer
{
    private static final int DEF_SIZE = 10;

    private int position;
    private int limit;
    private int capacity;

    private char[] buf;

    private boolean readMode;

    public MyCharBuffer()
    {
        this.buf = new char[DEF_SIZE];
        this.limit = this.capacity = DEF_SIZE;
    }

    /**
     * 翻转
     */
    public void flip()
    {
        if (readMode)
        {
            this.limit = this.capacity;
        } else
        {
            this.limit = this.position;
        }
        this.position = 0;
        this.readMode = !this.readMode;
    }

    /**
     * 清空
     */
    public void clear()
    {
        this.position = 0;
        this.limit = this.capacity;
        this.readMode = false;
    }

    /**
     * 重读
     */
    public void rewind()
    {
        this.position = 0;
    }

    public void putChar(char c)
    {
        checkReadMode(false);
        checkExpansion();
        if (position >= limit)
            throw new BufferOverflowException();
        buf[position++] = c;
    }

    public char readChar()
    {
        checkReadMode(true);
        if (position == limit)
            throw new BufferOverflowException();
        return buf[position++];
    }

    private void checkExpansion()
    {
        if (position >= capacity - 1)
        {
            char[] newBuf = Arrays.copyOf(this.buf, this.capacity * 2);
            this.capacity = this.limit = newBuf.length;
            this.buf = newBuf;
        }
    }

    private void checkReadMode(boolean mode)
    {
        if (mode != this.readMode) throw new RuntimeException("当前读写模式不支持此操作！");
    }
}
