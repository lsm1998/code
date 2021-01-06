package com.lsm1998.echoes.rpc.client;

import com.lsm1998.echoes.registry.client.RegistryClient;

public class ReferenceClientBuild
{
    private RegistryClient registry;
    private String serviceName;

    public ReferenceClientBuild registry(RegistryClient registry)
    {
        this.registry = registry;
        return this;
    }

    public ReferenceClientBuild serviceName(String serviceName)
    {
        this.serviceName = serviceName;
        return this;
    }

    public ReferenceClient build()
    {
        return new DefaultReferenceClient(this.serviceName,this.registry);
    }
}
