package com.lsm1998.log4j;

public interface Logger
{
    void trace(String msg);

    void info(String msg);

    void debug(String msg);

    void warn(String msg);

    void error(String msg);

    String getName();
}
