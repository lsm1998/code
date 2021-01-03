package com.lsm1998.echoes.registry.facade;

import com.lsm1998.echoes.registry.bean.RegistryNodeBean;
import com.lsm1998.echoes.registry.bean.RegistryServiceBean;

import java.util.ArrayList;
import java.util.Date;

public class NodeList extends ArrayList<RegistryNodeBean>
{
    public int addNode(RegistryServiceBean serviceBean, String ip, Integer port)
    {
        String key = String.format("%s-%s-%d", serviceBean.getServiceName(), ip, port);
        if (check(key))
        {
            return 0;
        }
        this.add(RegistryNodeBean.of(serviceBean.getUuid(), key, ip, port));
        return 1;
    }

    public boolean check(String key)
    {
        for (RegistryNodeBean v : this)
        {
            if (v.getKey().equals(key))
            {
                v.setLastPingDate(new Date());
                return true;
            }
        }
        return false;
    }

    public int removeNode(RegistryServiceBean serviceBean, String ip, Integer port)
    {
        String key = String.format("%s-%s-%d", serviceBean.getServiceName(), ip, port);
        if (check(key))
        {
            this.removeIf(v -> v.getKey().equals(key));
            return 1;
        }
        return 0;
    }
}
