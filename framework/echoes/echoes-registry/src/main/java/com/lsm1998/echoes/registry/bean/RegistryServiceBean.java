package com.lsm1998.echoes.registry.bean;

import com.lsm1998.echoes.registry.facade.NodeList;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@Data
public class RegistryServiceBean implements Serializable
{
    private String uuid;
    private String serviceName;
    private Date createDate;
    private NodeList nodeList;

    public static RegistryServiceBean of(String serviceName)
    {
        RegistryServiceBean result = new RegistryServiceBean();
        result.serviceName = serviceName;
        result.createDate = new Date();
        result.uuid = UUID.randomUUID().toString();
        result.nodeList = new NodeList();
        return result;
    }
}
