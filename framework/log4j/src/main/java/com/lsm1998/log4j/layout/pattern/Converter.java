package com.lsm1998.log4j.layout.pattern;

import com.lsm1998.log4j.LoggingEvent;

public interface Converter
{
    String convert(LoggingEvent e);
}
