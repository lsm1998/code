package com.lsm1998.log4j.filter;

import com.lsm1998.log4j.LifeCycle;
import com.lsm1998.log4j.LoggingEvent;

public interface Filter extends LifeCycle
{
    boolean doFilter(LoggingEvent event);
}
