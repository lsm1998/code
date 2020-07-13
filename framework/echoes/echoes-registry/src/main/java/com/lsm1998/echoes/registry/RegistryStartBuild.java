package com.lsm1998.echoes.registry;

import com.lsm1998.echoes.common.net.EchoesServer;
import com.lsm1998.echoes.common.net.nio.NIOServer;
import com.lsm1998.echoes.registry.config.RegistryConfig;
import com.lsm1998.echoes.registry.enums.LoadStrategy;
import com.lsm1998.echoes.registry.enums.SerializeStrategy;

/**
 * @program: code
 * @description:
 * @author: lsm
 * @create: 2020-06-24 09:50
 **/
public class RegistryStartBuild
{
    private static final int DEFAULT_PORT = 12222;
    private RegistryConfig defaultConfig;

    public RegistryStartBuild()
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
        this.defaultConfig.setPort(DEFAULT_PORT);
    }

    public RegistryStartBuild loadStrategy(LoadStrategy strategy)
    {
        defaultConfig.setLoadStrategy(strategy);
        return this;
    }

    public RegistryStartBuild serializeStrategy(SerializeStrategy strategy)
    {
        defaultConfig.setSerializeStrategy(strategy);
        return this;
    }

    public RegistryStartBuild clusterAdder(String[] clusterAdder)
    {
        defaultConfig.setClusterAdder(clusterAdder);
        return this;
    }

    public RegistryStartBuild cluster(boolean isCluster)
    {
        defaultConfig.setCluster(isCluster);
        return this;
    }

    public RegistryStartBuild port(int port)
    {
        defaultConfig.setPort(port);
        return this;
    }

    public RegistryStartBuild timeOut(int timeOut)
    {
        defaultConfig.setTimeOut(timeOut);
        return this;
    }

    public RegistryStartBuild serverClass(Class<? extends EchoesServer> serverClass)
    {
        defaultConfig.setServerClass(serverClass);
        return this;
    }

    public RegistryStart build()
    {
        return new RegistryStart(this.defaultConfig);
    }
}
