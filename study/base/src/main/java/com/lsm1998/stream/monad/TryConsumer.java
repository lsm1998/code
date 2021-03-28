package com.lsm1998.stream.monad;

public interface TryConsumer<T, E extends Throwable>
{

    /**
     * Performs this operation on the given argument.
     *
     * @param t the input argument
     */
    void accept(T t) throws E;

}