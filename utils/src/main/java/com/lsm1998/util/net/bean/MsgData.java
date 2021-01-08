package com.lsm1998.util.net.bean;

import lombok.Data;

import java.io.Serializable;

/**
 * @作者：刘时明
 * @时间：2019/6/8-14:09
 * @说明：网络传输消息体
 */
@Data
public class MsgData<E> implements Serializable
{
    private int code;
    private E data;

    public static <E> MsgData<E> of(int code,E data)
    {
        MsgData<E> msgData = new MsgData<>();
        msgData.setData(data);
        msgData.setCode(code);
        return msgData;
    }
}
