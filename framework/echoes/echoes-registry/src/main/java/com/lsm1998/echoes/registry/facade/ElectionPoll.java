package com.lsm1998.echoes.registry.facade;

import com.lsm1998.echoes.registry.bean.RegistryNodeBean;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class ElectionPoll implements ElectionStrategy
{
    private final AtomicInteger pollIndex;

    public ElectionPoll()
    {
        this.pollIndex = new AtomicInteger();
    }

    @Override
    public RegistryNodeBean election(List<RegistryNodeBean> nodeList)
    {
        int size = nodeList.size();
        if (size == 0)
        {
            return null;
        }
        return nodeList.get(pollIndex.addAndGet(1) % size);
    }
}
