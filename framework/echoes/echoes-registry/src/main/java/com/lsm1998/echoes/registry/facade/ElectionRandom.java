package com.lsm1998.echoes.registry.facade;

import com.lsm1998.echoes.registry.bean.RegistryNodeBean;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class ElectionRandom implements ElectionStrategy
{
    private final Random random;

    public ElectionRandom()
    {
        this.random = new Random();
    }

    @Override
    public RegistryNodeBean election(List<RegistryNodeBean> nodeList)
    {
        int size = nodeList.size();
        return nodeList.get(random.nextInt(size));
    }
}
