package com.lsm1998.log4j.layout.pattern;

import com.lsm1998.log4j.LoggingEvent;

public class ThreadConverter extends KeywordConverter
{
    @Override
    public String convert(LoggingEvent e)
    {
        return e.getThreadName();
    }
}
