package com.lsm1998.echoes.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

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

    public static EchoesConfig parse(Map map)
    {
        if(map==null)return null;
        if(!map.containsKey("echoes"))return null;
        Map<String,Object> echoesMap= (Map<String, Object>) map.get("echoes");
        if(!map.containsKey("rpc")||!map.containsKey("registry")) return null;
        EchoesConfig config=new EchoesConfig();
        config.setRegistry(new Registry());
        config.setRpc(new Rpc());
        Map<String,Object> rpcMap= (Map<String, Object>) echoesMap.get("rpc");
        Map<String,Object> registryMap= (Map<String, Object>) echoesMap.get("registry");

        return config;
    }
}
