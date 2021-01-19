package com.lsm1998.log4j.layout.pattern;

import com.lsm1998.log4j.LoggingEvent;

public class LevelConverter implements Converter
{
    @Override
    public String convert(LoggingEvent e)
    {
        return e.getLevel().toString();
    }

}
