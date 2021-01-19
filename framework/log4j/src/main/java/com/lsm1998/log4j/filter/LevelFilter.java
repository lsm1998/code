package com.lsm1998.log4j.filter;

import com.lsm1998.log4j.Level;
import com.lsm1998.log4j.LoggingEvent;

public class LevelFilter implements Filter
{
    private String level;

    private Level l;

    @Override
    public boolean doFilter(LoggingEvent event)
    {
        return event.getLevel().isGreaterOrEqual(l);
    }

    public void setLevel(String level)
    {
        this.level = level;
    }

    @Override
    public void start()
    {
        this.l = Level.parse(level);
    }

    @Override
    public void stop()
    {

    }
}
