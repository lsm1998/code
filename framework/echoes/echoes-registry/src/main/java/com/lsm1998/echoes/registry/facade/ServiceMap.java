package com.lsm1998.echoes.registry.facade;

import com.lsm1998.echoes.registry.bean.RegistryServiceBean;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ServiceMap
{
    private Map<String, RegistryServiceBean> serviceMap = new HashMap<>(16);

    public boolean create(String serviceName)
    {
        if (serviceMap.containsKey(serviceName))
        {
            return false;
        }
        serviceMap.put(serviceName, RegistryServiceBean.of(serviceName));
        return true;
    }

    public boolean delete(String serviceName)
    {
        return serviceMap.remove(serviceName) != null;
    }

    public RegistryServiceBean get(String serviceName)
    {
        return serviceMap.get(serviceName);
    }

    public Map<String, RegistryServiceBean> get()
    {
        return serviceMap;
    }
}
