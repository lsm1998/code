package com.lsm1998.echoes.rpc.invoke;

import com.lsm1998.echoes.rpc.client.DefaultReferenceClient;

import java.lang.reflect.Proxy;

/**
 * @作者：刘时明
 * @时间:2018/12/27-14:47
 * @说明：
 */
public class ProxyFactory
{
    public static <T> T getProxy(Class<?> clazz, DefaultReferenceClient client)
    {
        MyInvocationHandler handler = new MyInvocationHandler(clazz, client);
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, handler);
    }
}
