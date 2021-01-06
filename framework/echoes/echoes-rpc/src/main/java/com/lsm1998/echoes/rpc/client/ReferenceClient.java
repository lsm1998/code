package com.lsm1998.echoes.rpc.client;

import java.io.IOException;

public interface ReferenceClient
{
    <E> E getProxy(Class<E> clazz) throws IOException;
}
