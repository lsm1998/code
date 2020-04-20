package com.lsm1998.net.echoes.rpc.proxy;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @program: code
 * @description:
 * @author: lsm
 * @create: 2020-04-13 10:49
 **/
public class CglibInvocationHandler implements MethodInterceptor
{
    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable
    {
        // before(method,args);
        try
        {
            Object result = methodProxy.invokeSuper(o, objects);
            return result;
        }catch (Exception e)
        {
            // afterThrowing(method,args);
        }finally
        {
            // after(method,args);
        }
        return null;
    }
}
