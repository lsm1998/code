package com.lsm1998.echoes.registry.service;

import com.lsm1998.echoes.registry.bean.RegistryNodeBean;
import com.lsm1998.echoes.registry.facade.RegistryFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegistryService
{
    @Autowired
    private RegistryFacade registryFacade;

    public int registry(String serviceName, String ip, Integer port)
    {
        return registryFacade.joinNode(serviceName, ip, port);
    }

    public Object get()
    {
        return registryFacade.get();
    }

    public RegistryNodeBean electionNode(String serviceName)
    {
        return registryFacade.electionNode(serviceName);
    }
}
