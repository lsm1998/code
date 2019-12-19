package com.oo.domain;

import java.io.Serializable;
import java.util.Arrays;

/**
 * 作者：刘时明
 * 日期：2018/9/24
 * 时间：20:23
 * 说明：用户实体
 */
public class User implements Serializable
{
    private int id;
    private String nickName;
    private long accNumber;
    private String passWord;
    private char sex;
    private byte age;
    private String birthDay;
    private String autoGraph;
    private byte[] head_img;
    private String groupName;
    private int port;
    private String ipAddr;
    private byte flag;

    public byte[] getHead_img()
    {
        return head_img;
    }

    public void setHead_img(byte[] head_img)
    {
        this.head_img = head_img;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public char getSex()
    {
        return sex;
    }

    public void setSex(char sex)
    {
        this.sex = sex;
    }

    public byte getAge()
    {
        return age;
    }

    public void setAge(byte age)
    {
        this.age = age;
    }

    public String getNickName()
    {
        return nickName;
    }

    public void setNickName(String nickName)
    {
        this.nickName = nickName;
    }

    public long getAccNumber()
    {
        return accNumber;
    }

    public void setAccNumber(long accNumber)
    {
        this.accNumber = accNumber;
    }

    public String getPassWord()
    {
        return passWord;
    }

    public void setPassWord(String passWord)
    {
        this.passWord = passWord;
    }

    public String getBirthDay()
    {
        return birthDay;
    }

    public void setBirthDay(String birthDay)
    {
        this.birthDay = birthDay;
    }

    public String getAutoGraph()
    {
        return autoGraph;
    }

    public void setAutoGraph(String autoGraph)
    {
        this.autoGraph = autoGraph;
    }

    public int getPort()
    {
        return port;
    }

    public void setPort(int port)
    {
        this.port = port;
    }

    public String getIpAddr()
    {
        return ipAddr;
    }

    public void setIpAddr(String ipAddr)
    {
        this.ipAddr = ipAddr;
    }

    public String getGroupName()
    {
        return groupName;
    }

    public void setGroupName(String groupName)
    {
        this.groupName = groupName;
    }

    public byte getFlag()
    {
        return flag;
    }

    public void setFlag(byte flag)
    {
        this.flag = flag;
    }

    @Override
    public String toString()
    {
        return "User{" +
                "id=" + id +
                ", nickName='" + nickName + '\'' +
                ", accNumber=" + accNumber +
                ", passWord='" + passWord + '\'' +
                ", sex=" + sex +
                ", age=" + age +
                ", birthDay='" + birthDay + '\'' +
                ", autoGraph='" + autoGraph + '\'' +
                ", groupName='" + groupName + '\'' +
                ", port=" + port +
                ", ipAddr='" + ipAddr + '\'' +
                ", flag=" + flag +
                ", head_img=" + Arrays.toString(head_img) +
                '}';
    }
}
