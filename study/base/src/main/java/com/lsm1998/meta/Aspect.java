package com.lsm1998.meta;

import com.lsm1998.util.stream.Try;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 实现一个AOP代理
 */
public interface Aspect
{
    void before();

    void after();

    static <T> T getProxy(Class<T> cls, String... aspects) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException
    {
        List<Try<Aspect>> collect = Arrays.stream(aspects).map(name ->
                Try.ofFailable(() ->
                {
                    Class<?> clazz = Class.forName(name);
                    return (Aspect) clazz.getConstructor().newInstance();
                })).filter(Try::isSuccess).collect(Collectors.toList());
        T instance = cls.getConstructor().newInstance();
        return (T) Proxy.newProxyInstance(cls.getClassLoader(),
                cls.getInterfaces(), (proxy, method, args) ->
                {
                    for (var t : collect)
                    {
                        t.get().before();
                    }
                    Object result = method.invoke(instance);
                    for (var t : collect)
                    {
                        t.get().after();
                    }
                    return result;
                });
    }
}
