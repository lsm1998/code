package com.lsm1998.net.echoes.rpc.context;

import com.lsm1998.net.echoes.config.EchoesConfig;
import com.lsm1998.net.echoes.rpc.bean.ProxyBean;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: code
 * @description:
 * @author: lsm
 * @create: 2020-04-01 18:26
 **/
@Slf4j
public class DefaultEchoesService extends AbstractEchoesService
{
    private EchoesConfig echoesConfig;
    private Map<String, ProxyBean<?>> targetObjMap=new HashMap<>(16);

    protected DefaultEchoesService(EchoesConfig echoesConfig)
    {
        this.echoesConfig=echoesConfig;
    }

    @Override
    public void export()
    {
        // 1.注册RPC代理类
        classProxy(echoesConfig.getRpc());

        // 2.连接配置中心
        connectRegistry(echoesConfig.getRpc().getServiceName(),echoesConfig.getRegistry());
    }

    @Override
    void classProxy(EchoesConfig.Rpc rpc)
    {
        RpcProxy rpcProxy=new RpcProxy(rpc);
        rpcProxy.classProxy(targetObjMap);
        log.info("RPC代理类注册完成");
    }

    @Override
    void connectRegistry(String serviceName,EchoesConfig.Registry registry)
    {
        RegistryHandler handler=new RegistryHandler();
        try
        {
            handler.connect(registry.getAdder(),registry.getPort());
        }catch (IOException e)
        {
            e.printStackTrace();
        }
        handler.report(serviceName,registry.getAdder(),registry.getPort(),targetObjMap);
        log.info("注册中心连接完毕");
    }
}
