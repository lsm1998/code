/*
 * 作者：刘时明
 * 时间：2020/3/8-16:11
 * 作用：
 */
package com.lsm1998.echoes.registry.service;

import com.lsm1998.echoes.common.net.EchoesServer;
import com.lsm1998.echoes.common.net.nio.NIOServer;
import com.lsm1998.echoes.registry.config.RegistryConfig;

public class RegistryServerBuild
{
    private RegistryConfig defaultConfig;

    public RegistryServerBuild()
    {
        this.defaultConfig = new RegistryConfig();
        this.defaultConfig.setLoadStrategy(1);
        this.defaultConfig.setTimeOut(5 * 1000);
        this.defaultConfig.setServerClass(NIOServer.class);
    }

    public RegistryServerBuild loadStrategy(int strategy)
    {
        defaultConfig.setLoadStrategy(strategy);
        return this;
    }

    public RegistryServerBuild timeOut(int timeOut)
    {
        defaultConfig.setTimeOut(timeOut);
        return this;
    }

    public RegistryServerBuild serverClass(Class<? extends EchoesServer> serverClass)
    {
        defaultConfig.setServerClass(serverClass);
        return this;
    }

    public RegistryServer build()
    {
        return new RegistryServer(this.defaultConfig);
    }
}
