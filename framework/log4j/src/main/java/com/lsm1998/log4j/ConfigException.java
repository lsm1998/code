package com.lsm1998.log4j;

public class ConfigException extends RuntimeException
{
    public ConfigException(Exception e)
    {
        super(e);
    }

    public ConfigException(String message)
    {
        super(message);
    }
}
