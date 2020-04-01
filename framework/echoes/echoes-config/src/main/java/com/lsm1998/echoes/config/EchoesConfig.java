package com.lsm1998.echoes.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @program: code
 * @description:
 * @author: lsm
 * @create: 2020-04-01 17:52
 **/
@Slf4j
@Data
public class EchoesConfig
{
    private Registry registry;
    private Rpc rpc;

    @Data
    public static class Registry
    {
        private String serviceName;
        private int port;
    }

    @Data
    public static class Rpc
    {
        private String scanPackage;
        private String adder;
        private int port;
    }
}
