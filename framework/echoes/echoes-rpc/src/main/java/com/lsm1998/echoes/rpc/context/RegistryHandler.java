package com.lsm1998.echoes.rpc.context;

import com.lsm1998.echoes.registry.bean.AppReport;
import com.lsm1998.echoes.registry.client.RegistryClient;
import com.lsm1998.echoes.rpc.bean.ProxyBean;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.List;
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
        client=new RegistryClient(adder,port);
    }

    public void report(String serviceName, String adder, int port, Map<String, ProxyBean<?>> targetObjMap)
    {
        targetObjMap.forEach((k,v)->
        {
            client.registryApp(serviceName,adder,port);
            log.info("注册服务:{}",serviceName);
        });
        List<AppReport> appReports = client.queryApp(serviceName);
        System.out.println("appReports="+appReports);
    }
}
