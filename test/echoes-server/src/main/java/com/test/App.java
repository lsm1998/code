/*
 * 作者：刘时明
 * 时间：2019/12/21-16:23
 * 作用：
 */
package com.test;

import com.lsm1998.echoes.config.EchoesConfigStart;
import com.lsm1998.echoes.rpc.EchoesService;
import com.lsm1998.echoes.rpc.context.EchoesServiceBuild;

public class App
{
    public static void main(String[] args)
    {
        EchoesConfigStart echoesConfig=new EchoesConfigStart();
        // 使用默认的方式加载配置文件
        echoesConfig.load();

        // 启动Service
        EchoesService service=new EchoesServiceBuild()
                .echoesConfig(echoesConfig)
                .build();

        // 暴露及注册服务
        service.export();
    }
}
