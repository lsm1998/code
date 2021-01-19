package com.lsm1998.log4j.appender;

public interface AppenderAttachable
{
    void addAppender(Appender newAppender);

    Appender getAppender(String name);

    boolean isAttached(Appender appender);

    void removeAppender(Appender appender);

    void removeAppender(String name);
}

