package com.lsm1998.log4j.config;

import com.lsm1998.log4j.LoggerContext;

import java.net.URL;

public class YAMLConfigurator implements Configurator
{
    private final URL url;

    private final LoggerContext loggerContext;

    public YAMLConfigurator(URL url, LoggerContext loggerContext)
    {
        this.url = url;
        this.loggerContext = loggerContext;
    }

    @Override
    public void doConfigure()
    {

    }

    @Override
    public void start()
    {

    }

    @Override
    public void stop()
    {

    }
}
