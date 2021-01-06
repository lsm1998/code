package com.lsm1998.echoes.rpc;

import com.lsm1998.echoes.common.net.EchoesClient;
import com.lsm1998.echoes.common.net.RpcCallResponse;

import java.io.IOException;

public interface EchoesRpcClient extends EchoesClient
{
    RpcCallResponse rpcCall(String className,String methodName, Object[] args) throws IOException;
}
