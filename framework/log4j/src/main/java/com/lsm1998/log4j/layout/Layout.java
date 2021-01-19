package com.lsm1998.log4j.layout;

import com.lsm1998.log4j.LifeCycle;
import com.lsm1998.log4j.LoggingEvent;

public interface Layout extends LifeCycle
{
    String doLayout(LoggingEvent e);
}
