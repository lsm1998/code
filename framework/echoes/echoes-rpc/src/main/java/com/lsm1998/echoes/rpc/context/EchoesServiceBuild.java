package com.lsm1998.echoes.rpc.context;

import com.lsm1998.echoes.config.EchoesConfigStart;

/**
 * @program: code
 * @description:
 * @author: lsm
 * @create: 2020-04-01 18:14
 **/
public class EchoesServiceBuild
{
    private EchoesConfigStart echoesConfig;

    public DefaultEchoesService build()
    {
        return new DefaultEchoesService(echoesConfig.config());
    }

    public EchoesServiceBuild echoesConfig(EchoesConfigStart echoesConfig)
    {
        this.echoesConfig=echoesConfig;
        return this;
    }
}
