package com.lsm1998.util.stream;

public interface TryMapFunction<T, R>
{
    R apply(T t) throws Throwable;
}