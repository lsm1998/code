package com.lsm1998.echoes.common.net;

import lombok.Data;

import java.io.Serializable;

@Data
public class RpcCallRequest implements Serializable
{
    private String className;

    private String methodName;

    private Object[] args;

    public static RpcCallRequest of(String className, String methodName, Object[] args)
    {
        RpcCallRequest request = new RpcCallRequest();
        request.setMethodName(methodName);
        request.setClassName(className);
        request.setArgs(args);
        return request;
    }
}
