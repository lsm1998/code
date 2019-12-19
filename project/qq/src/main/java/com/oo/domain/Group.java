package com.oo.domain;

import java.io.Serializable;

/**
 * 作者：刘时明
 * 日期：2018/9/24
 * 时间：20:36
 * 说明：分组实体
 */
public class Group implements Serializable
{
    private int id;
    private long accNumber;
    private String groupName;

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getGroupName()
    {
        return groupName;
    }

    public void setGroupName(String groupName)
    {
        this.groupName = groupName;
    }

    public long getAccNumber()
    {
        return accNumber;
    }

    public void setAccNumber(long accNumber)
    {
        this.accNumber = accNumber;
    }

    @Override
    public String toString()
    {
        return "Group{" +
                "id=" + id +
                ", accNumber=" + accNumber +
                ", groupName='" + groupName + '\'' +
                '}';
    }
}
