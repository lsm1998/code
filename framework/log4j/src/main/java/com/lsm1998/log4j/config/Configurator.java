package com.lsm1998.log4j.config;

import com.lsm1998.log4j.LifeCycle;

public interface Configurator extends LifeCycle
{
    void doConfigure();
}
