package com.lsm1998.log4j.layout.pattern;

import com.lsm1998.log4j.LoggingEvent;

public class LiteralConverter implements Converter
{

    private String literal;

    @Override
    public String convert(LoggingEvent e)
    {
        return literal;
    }

    public LiteralConverter(String literal)
    {
        this.literal = literal;
    }
}
