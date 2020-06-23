package com.test;

import com.lsm1998.net.echoes.common.net.EchoesServer;
import com.lsm1998.net.echoes.common.net.nio.NIOServer;
import com.lsm1998.net.echoes.registry.Define;
import com.lsm1998.net.echoes.registry.enums.LoadStrategy;
import com.lsm1998.net.echoes.registry.service.RegistryServerBuild;

import java.io.IOException;

/**
 * @program: code
 * @description:
 * @author: lsm
 * @create: 2020-04-13 11:30
 **/
public class Registry
{
    public static void main(String[] args) throws IOException
    {
        EchoesServer server = new RegistryServerBuild()
                .serverClass(NIOServer.class)
                .loadStrategy(LoadStrategy.RANDOM)
                .build();
        server.start();
    }
}
