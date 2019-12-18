package com.oo.yuyin;

import java.io.Serializable;

/**
 * 作者：刘时明
 * 日期：2018/10/6
 * 时间：1:55
 * 说明：
 */
public class Audio implements Serializable
{
    private long acc;
    private byte[] b;
    private int len;

    public Audio(long acc, byte[] b,int len)
    {
        this.acc = acc;
        this.len=len;
        this.b = b;
    }

    public int getLen()
    {
        return len;
    }

    public void setLen(int len)
    {
        this.len = len;
    }

    public long getAcc()
    {
        return acc;
    }

    public void setAcc(long acc)
    {
        this.acc = acc;
    }

    public byte[] getB()
    {
        return b;
    }

    public void setB(byte[] b)
    {
        this.b = b;
    }
}
