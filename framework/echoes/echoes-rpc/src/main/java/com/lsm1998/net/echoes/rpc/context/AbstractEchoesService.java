package com.lsm1998.net.echoes.rpc.context;

import com.lsm1998.net.echoes.config.EchoesConfig;
import com.lsm1998.net.echoes.rpc.EchoesService;

/**
 * @program: code
 * @description:
 * @author: lsm
 * @create: 2020-04-01 18:26
 **/
public abstract class AbstractEchoesService implements EchoesService
{
    // 注册RPC代理类
    abstract void classProxy(EchoesConfig.Rpc rpc);

    // 连接配置中心
    abstract void connectRegistry(String serviceName,EchoesConfig.Registry registry);
}
