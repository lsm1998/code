package com.lsm1998.echoes.rpc.invoke;

import com.lsm1998.echoes.common.net.NullVal;
import com.lsm1998.echoes.common.net.RpcCallResponse;
import com.lsm1998.echoes.rpc.client.DefaultReferenceClient;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.sql.Connection;

/**
 * @作者：刘时明
 * @时间:2018/12/27-14:46
 * @说明：
 */
public class MyInvocationHandler<T> implements InvocationHandler
{
    private final T target;
    private final DefaultReferenceClient client;

    protected MyInvocationHandler(T target, DefaultReferenceClient client)
    {
        this.target = target;
        this.client = client;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args)
    {
        try
        {
            RpcCallResponse response = client.rpcCall(((Class<?>) target).getName(), method.getName(), args, method.getParameterTypes());
            Object data = response.getData();
            return data == NullVal.Instance ? null : data;
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
