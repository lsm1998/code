package com.lsm1998.echoes.rpc.context;

import com.lsm1998.echoes.config.EchoesConfig;

/**
 * @program: code
 * @description:
 * @author: lsm
 * @create: 2020-04-01 18:14
 **/
public class EchoesServiceBuild
{
    private EchoesConfig echoesConfig;

    public DefaultEchoesService build()
    {
        return new DefaultEchoesService(echoesConfig);
    }

    public EchoesServiceBuild echoesConfig(EchoesConfig echoesConfig)
    {
        this.echoesConfig=echoesConfig;
        return this;
    }
}
