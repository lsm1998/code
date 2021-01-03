package com.lsm1998.echoes.registry.facade;

import com.lsm1998.echoes.registry.bean.RegistryNodeBean;
import com.lsm1998.echoes.registry.enums.LoadStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ElectionStrategyContext
{
    @Autowired
    private ElectionPoll electionPoll;

    @Autowired
    private ElectionRandom electionRandom;

    public RegistryNodeBean electionNode(LoadStrategy strategy, List<RegistryNodeBean> nodeList)
    {
        if (strategy == LoadStrategy.POLL)
        {
            return electionPoll.election(nodeList);
        } else if (strategy == LoadStrategy.RANDOM)
        {
            return electionRandom.election(nodeList);
        } else
        {
            return null;
        }
    }
}
