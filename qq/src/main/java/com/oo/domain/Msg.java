package com.oo.domain;

import java.io.Serializable;

/**
 * 作者：刘时明
 * 日期：2018/10/8
 * 时间：12:13
 * 说明：
 */
public class Msg implements Serializable
{
    private int id;
    private long myId;
    private long friendId;
    private byte[] content;
    private byte flag;

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public long getMyId()
    {
        return myId;
    }

    public void setMyId(long myId)
    {
        this.myId = myId;
    }

    public long getFriendId()
    {
        return friendId;
    }

    public void setFriendId(long friendId)
    {
        this.friendId = friendId;
    }

    public byte[] getContent()
    {
        return content;
    }

    public void setContent(byte[] content)
    {
        this.content = content;
    }

    public byte getFlag()
    {
        return flag;
    }

    public void setFlag(byte flag)
    {
        this.flag = flag;
    }
}
