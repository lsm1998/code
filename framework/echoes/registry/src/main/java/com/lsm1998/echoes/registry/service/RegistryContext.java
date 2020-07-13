package com.lsm1998.echoes.registry.service;

import com.lsm1998.echoes.registry.bean.MethodReport;
import com.lsm1998.echoes.registry.bean.AppReport;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: code
 * @description:
 * @author: lsm
 * @create: 2020-04-01 17:18
 **/
public class RegistryContext
{
    private static final RegistryContext context=new RegistryContext();
    private static final Map<String, List<AppReport>> serviceMap = new HashMap<>();
    private static final Map<String, List<MethodReport>> methodMap = new HashMap<>();

    private RegistryContext(){}

    public static RegistryContext getInstance()
    {
        return context;
    }

    public List<AppReport> getServiceMap(String data)
    {
        return serviceMap.get(data);
    }

    public void putServiceMap(String data,List<AppReport> list)
    {
        serviceMap.put(data, list);
    }

    public void putMethodMap(String data,List<MethodReport> list)
    {
        methodMap.put(data, list);
    }

    public boolean serviceMapContainsKey(String key)
    {
        return serviceMap.containsKey(key);
    }

    public boolean methodMapContainsKey(String key)
    {
        return methodMap.containsKey(key);
    }

    public List<MethodReport> getMethodMap(String data)
    {
        return methodMap.get(data);
    }
}
