package com.lsm1998.echoes.rpc.context;

import com.lsm1998.echoes.config.EchoesConfig;

/**
 * @program: code
 * @description:
 * @author: lsm
 * @create: 2020-04-01 18:26
 **/
public class DefaultEchoesService extends AbstractEchoesService
{
    private EchoesConfig echoesConfig;

    protected DefaultEchoesService(EchoesConfig echoesConfig)
    {
        this.echoesConfig=echoesConfig;
    }

    @Override
    public void export()
    {
        // 1.注册RPC代理类
        classProxy(echoesConfig.getRpc());
    }

    @Override
    void classProxy(EchoesConfig.Rpc rpc)
    {
        assert rpc!=null;
        String packagePath=rpc.getScanPackage();
        String packageDirName = packagePath.replace('.', '/');
        // Class<?>[] clazzs=
    }
}
