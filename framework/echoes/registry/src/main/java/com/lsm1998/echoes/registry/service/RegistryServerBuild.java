/*
 * 作者：刘时明
 * 时间：2020/3/8-16:11
 * 作用：
 */
package com.lsm1998.echoes.registry.service;

import com.lsm1998.echoes.common.net.EchoesServer;
import com.lsm1998.echoes.common.net.nio.NIOServer;
import com.lsm1998.echoes.registry.config.RegistryConfig;
import com.lsm1998.echoes.registry.enums.LoadStrategy;
import com.lsm1998.echoes.registry.enums.SerializeStrategy;

public class RegistryServerBuild
{
    private RegistryConfig defaultConfig;

    public RegistryServerBuild()
    {
        /**
         * 默认配置
         */
        this.defaultConfig = new RegistryConfig();
        this.defaultConfig.setLoadStrategy(LoadStrategy.POLL);
        this.defaultConfig.setTimeOut(5 * 1000);
        this.defaultConfig.setServerClass(NIOServer.class);
        this.defaultConfig.setCluster(false);
        this.defaultConfig.setClusterAdder(new String[]{});
        this.defaultConfig.setSerializeStrategy(SerializeStrategy.JSON);
    }

    public RegistryServerBuild loadStrategy(LoadStrategy strategy)
    {
        defaultConfig.setLoadStrategy(strategy);
        return this;
    }

    public RegistryServerBuild serializeStrategy(SerializeStrategy strategy)
    {
        defaultConfig.setSerializeStrategy(strategy);
        return this;
    }

    public RegistryServerBuild clusterAdder(String[] clusterAdder)
    {
        defaultConfig.setClusterAdder(clusterAdder);
        return this;
    }

    public RegistryServerBuild cluster(boolean isCluster)
    {
        defaultConfig.setCluster(isCluster);
        return this;
    }

    public RegistryServerBuild port(int port)
    {
        defaultConfig.setPort(port);
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
