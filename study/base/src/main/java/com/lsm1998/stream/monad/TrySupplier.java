package com.lsm1998.stream.monad;

public interface TrySupplier<T>
{
    T get() throws Throwable;
}
