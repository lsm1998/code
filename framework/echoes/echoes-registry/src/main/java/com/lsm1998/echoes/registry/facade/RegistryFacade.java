package com.lsm1998.echoes.registry.facade;

import com.lsm1998.echoes.registry.bean.RegistryNodeBean;
import com.lsm1998.echoes.registry.bean.RegistryServiceBean;

import java.util.Map;

public interface RegistryFacade
{
    int joinNode(String serviceName, String ip, Integer port);

    int removeNode(String serviceName, String ip, Integer port);

    Map<String, RegistryServiceBean> get();

    RegistryNodeBean electionNode(String serviceName);

    RegistryServiceBean get(String serviceName);
}
