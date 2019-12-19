package com.lsm1998.tomcat.util;

public class ExceptionUtil
{
    public static RuntimeException wrap(Exception ex)
    {
        return new RuntimeException(ex);
    }
}
