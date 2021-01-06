package com.lsm1998.echoes.common.net;

import lombok.Data;

import java.io.Serializable;

@Data
public class RpcCallResponse implements Serializable
{
    private Object data;

    public static <E> RpcCallResponse of(E data)
    {
        RpcCallResponse response = new RpcCallResponse();
        response.setData(data);
        return response;
    }
}
