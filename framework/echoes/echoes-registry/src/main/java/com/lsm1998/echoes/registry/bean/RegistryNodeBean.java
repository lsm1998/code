package com.lsm1998.echoes.registry.bean;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class RegistryNodeBean implements Serializable
{
    private String key;
    private String ip;
    private Integer port;
    private String serviceId;

    private Date lastPingDate;

    public static RegistryNodeBean of(String serviceId, String key, String ip, Integer port)
    {
        RegistryNodeBean result = new RegistryNodeBean();
        result.setLastPingDate(new Date());
        result.setKey(key);
        result.setServiceId(serviceId);
        result.setIp(ip);
        result.setPort(port);
        return result;
    }
}
