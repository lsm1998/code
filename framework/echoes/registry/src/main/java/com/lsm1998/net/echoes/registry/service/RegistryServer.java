/*
 * 作者：刘时明
 * 时间：2020/3/8-15:51
 * 作用：
 */
package com.lsm1998.net.echoes.registry.service;

import com.lsm1998.net.echoes.common.net.EchoesServer;
import com.lsm1998.net.echoes.common.net.nio.NIOServer;
import com.lsm1998.net.echoes.registry.config.RegistryConfig;
import com.lsm1998.net.echoes.registry.service.impl.BIORegistryServerImpl;
import com.lsm1998.net.echoes.registry.service.impl.NIORegistryServerImpl;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
@Getter
public class RegistryServer implements EchoesServer
{
    private RegistryConfig config;
    private EchoesServer server;

    public RegistryServer(RegistryConfig config)
    {
        this.config = config;
    }

    @Override
    public void start(int port) throws IOException
    {
        log.info("注册中心开始启动");
        if (config.getServerClass() == NIOServer.class)
        {
            server = new NIORegistryServerImpl(config);
        } else
        {
            server = new BIORegistryServerImpl(config);
        }
        server.start(port);
    }

    @Override
    public void stop() throws IOException
    {
        log.info("注册中心关闭");
        server.stop();
    }
}
