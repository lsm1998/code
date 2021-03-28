package com.lsm1998.stream.monad;

public interface TryMapFunction<T, R>
{
    R apply(T t) throws Throwable;
}