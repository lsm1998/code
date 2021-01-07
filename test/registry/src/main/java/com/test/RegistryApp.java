package com.test;

import com.lsm1998.echoes.registry.RegistryStart;
import com.lsm1998.echoes.registry.RegistryStartBuild;

public class RegistryApp
{
    public static void main(String[] args) throws Exception
    {
        /**
         * 通过12221端口启动注册中心
         */
        RegistryStart start = new RegistryStartBuild().port(12221).build();
        start.start();
    }
}
