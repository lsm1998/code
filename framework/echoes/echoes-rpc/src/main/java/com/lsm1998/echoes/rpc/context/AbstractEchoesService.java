package com.lsm1998.echoes.rpc.context;

import com.lsm1998.echoes.config.EchoesConfig;
import com.lsm1998.echoes.rpc.EchoesService;

import java.io.IOException;

/**
 * @program: code
 * @description:
 * @author: lsm
 * @create: 2020-04-01 18:26
 **/
public abstract class AbstractEchoesService implements EchoesService
{
    // 注册RPC代理类
    abstract void classProxy();

    // 连接配置中心
    abstract void connectRegistry(String serviceName);

    // 启动服务
    abstract void startService() throws IOException;
}
