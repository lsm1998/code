package com.lsm1998.log4j;

public interface ILoggerFactory
{
    Logger getLogger(Class<?> clazz);

    Logger getLogger(String name);

    Logger newLogger(String name);
}
