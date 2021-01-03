package com.lsm1998.echoes.rpc.context;

import com.lsm1998.echoes.rpc.bean.ProxyBean;
import com.lsm1998.echoes.registry.client.RegistryClient;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Map;

/**
 * @program: code
 * @description:
 * @author: lsm
 * @create: 2020-04-13 11:34
 **/
@Slf4j
public class RegistryHandler
{
    private RegistryClient client;

    public void connect(String adder, int port) throws IOException
    {
        client = new RegistryClient(adder, port);
    }

    public void report(String serviceName, String adder, int port, Map<String, ProxyBean<?>> targetObjMap)
    {
        targetObjMap.forEach((k, v) ->
        {
            client.registry(serviceName, port);
            log.info("注册服务:{}", serviceName);
        });
    }
}
