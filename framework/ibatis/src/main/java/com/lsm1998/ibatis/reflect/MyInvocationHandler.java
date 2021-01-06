package com.lsm1998.ibatis.reflect;

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
    private T target;
    private Connection connection;

    protected MyInvocationHandler(T target, Connection connection)
    {
        this.target = target;
        this.connection = connection;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args)
    {
        Object result = MyAnalyticMethod.invoke(method, args, connection);
        return result;
    }
}
