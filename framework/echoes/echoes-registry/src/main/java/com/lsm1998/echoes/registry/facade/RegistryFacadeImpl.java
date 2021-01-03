package com.lsm1998.echoes.registry.facade;

import com.lsm1998.echoes.registry.bean.RegistryNodeBean;
import com.lsm1998.echoes.registry.bean.RegistryServiceBean;
import com.lsm1998.echoes.registry.config.RegistryConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class RegistryFacadeImpl implements RegistryFacade
{
    @Autowired
    private ServiceMap serviceMap;

    @Autowired
    private RegistryConfig registryConfig;

    @Autowired
    private ElectionStrategyContext electionStrategyContext;

    @Override
    public int joinNode(String serviceName, String ip, Integer port)
    {
        // 新增服务
        serviceMap.create(serviceName);

        // 新增节点
        RegistryServiceBean serviceBean = serviceMap.get(serviceName);
        return serviceBean.getNodeList().addNode(serviceBean, ip, port);
    }

    @Override
    public int removeNode(String serviceName, String ip, Integer port)
    {
        RegistryServiceBean serviceBean = serviceMap.get(serviceName);
        if (serviceBean == null)
        {
            return 0;
        }
        return serviceBean.getNodeList().removeNode(serviceBean, ip, port);
    }

    @Override
    public Map<String, RegistryServiceBean> get()
    {
        return serviceMap.get();
    }

    @Override
    public RegistryServiceBean get(String serviceName)
    {
        return serviceMap.get(serviceName);
    }

    @Override
    public RegistryNodeBean electionNode(String serviceName)
    {
        RegistryServiceBean serviceBean = this.get(serviceName);
        if (serviceBean == null)
        {
            return null;
        }
        return electionStrategyContext.electionNode(registryConfig.getLoadStrategy(), serviceBean.getNodeList());
    }
}
