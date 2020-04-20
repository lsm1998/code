package com.lsm1998.net.echoes.rpc.proxy;

import net.sf.cglib.proxy.Enhancer;

/**
 * @program: code
 * @description:
 * @author: lsm
 * @create: 2020-04-13 10:52
 **/
public class ProxyInstanceFactory
{
    public static <T> T getInstance(T instance)
    {
        CglibInvocationHandler handler = new CglibInvocationHandler();
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(instance.getClass());
        enhancer.setCallback(handler);
        return (T) enhancer.create();
    }
}
