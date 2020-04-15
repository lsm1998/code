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
public class EchoesConfig {
    private Registry registry;
    private Rpc rpc;

    @Data
    public static class Registry {
        private String adder;
        private int port;
    }

    @Data
    public static class Rpc {
        private String scanPackage;
        private String serviceName;
        private int port;
    }

    public static EchoesConfig parse(Map map) {
        if (map == null) return null;
        if (!map.containsKey("echoes")) return null;
        try {
            Map<String, Object> echoesMap = (Map<String, Object>) map.get("echoes");
            if (!echoesMap.containsKey("rpc") || !echoesMap.containsKey("registry")) return null;
            EchoesConfig config = new EchoesConfig();
            Registry registry = new Registry();
            Rpc rpc = new Rpc();
            Map<String, Object> rpcMap = (Map<String, Object>) echoesMap.get("rpc");
            rpc.setServiceName(rpcMap.get("serviceName").toString());
            String port = rpcMap.get("port").toString();
            rpc.setPort(Integer.parseInt(port));
            rpc.setScanPackage(rpcMap.get("scanPackage").toString());
            Map<String, Object> registryMap = (Map<String, Object>) echoesMap.get("registry");
            registry.setAdder(registryMap.get("adder").toString());
            port = registryMap.get("port").toString();
            registry.setPort(Integer.parseInt(port));
            config.setRegistry(registry);
            config.setRpc(rpc);
            return config;
        } catch (Exception e) {
            return null;
        }
    }
}
