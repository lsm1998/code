package com.lsm1998.util.net.bean;

import java.io.Serializable;

/**
 * @作者：刘时明
 * @时间：2019/6/8-14:09
 * @说明：网络传输消息体
 */
public class MsgData<E> implements Serializable
{
    public int code;
    public String msg;
    public E data;
}
