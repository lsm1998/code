package com.lsm1998.util.stream;

public interface TrySupplier<T>
{
    T get() throws Throwable;
}
