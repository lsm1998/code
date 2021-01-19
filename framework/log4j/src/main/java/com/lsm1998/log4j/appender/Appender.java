package com.lsm1998.log4j.appender;

import com.lsm1998.log4j.LifeCycle;
import com.lsm1998.log4j.LoggingEvent;

public interface Appender extends LifeCycle
{
    String getName();

    void setName(String name);

    void append(LoggingEvent e);
}
