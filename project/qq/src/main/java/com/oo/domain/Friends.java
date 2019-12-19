package com.oo.domain;

import java.io.Serializable;

/**
 * 作者：刘时明
 * 日期：2018/9/24 0024
 * 时间：20:38
 * 说明：好友实体
 */
public class Friends implements Serializable
{
    private int id;
    private int myId;
    private int FriendsId;
    private int groupId;

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public int getMyId()
    {
        return myId;
    }

    public void setMyId(int myId)
    {
        this.myId = myId;
    }

    public int getFriendsId()
    {
        return FriendsId;
    }

    public void setFriendsId(int friendsId)
    {
        FriendsId = friendsId;
    }

    public int getGroupId()
    {
        return groupId;
    }

    public void setGroupId(int groupId)
    {
        this.groupId = groupId;
    }

    @Override
    public String toString()
    {
        return "Friends{" +
                "id=" + id +
                ", myId=" + myId +
                ", FriendsId=" + FriendsId +
                ", groupId=" + groupId +
                '}';
    }
}
