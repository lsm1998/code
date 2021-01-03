package com.lsm1998.echoes.registry.facade;

import com.lsm1998.echoes.registry.bean.RegistryNodeBean;

import java.util.List;

/**
 * 选举策略接口
 */
public interface ElectionStrategy
{
    RegistryNodeBean election(List<RegistryNodeBean> nodeList);
}
