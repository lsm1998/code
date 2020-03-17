/*
 * 作者：刘时明
 * 时间：2020/3/8-15:52
 * 作用：
 */
package com.lsm1998.echoes.registry;

import com.lsm1998.echoes.common.net.EchoesServer;
import com.lsm1998.echoes.common.net.nio.NIOServer;
import com.lsm1998.echoes.registry.service.Define;
import com.lsm1998.echoes.registry.service.RegistryServerBuild;

import java.io.IOException;

public class RegistryStart
{
    public static void main(String[] args) throws IOException
    {
        EchoesServer server = new RegistryServerBuild()
                .serverClass(NIOServer.class)
                .loadStrategy(1)
                .build();
        server.start(Define.PORT);
    }
}
