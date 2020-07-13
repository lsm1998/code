package com.lsm1998.echoes.registry;

import com.lsm1998.echoes.common.net.EchoesServer;
import com.lsm1998.echoes.registry.config.RegistryConfig;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;

/**
 * @program: code
 * @description:
 * @author: lsm
 * @create: 2020-06-24 09:38
 **/
@SpringBootApplication
@Data
@Slf4j
public class RegistryStart implements EchoesServer
{
    private RegistryConfig config;
    private ConfigurableApplicationContext applicationContext;

    public RegistryStart(){}

    public RegistryStart(RegistryConfig config)
    {
        this.config = config;
    }

    public void start() throws IOException
    {
        this.start(this.config.getPort());
    }

    @Override
    public void start(int port) throws IOException
    {
        applicationContext = SpringApplication.run(RegistryStart.class, String.format("--server.port=%d", port));
        // BeanDefinitionRegistry beanDefinitionRegistry = (DefaultListableBeanFactory) applicationContext.getBeanFactory();
    }

    @Override
    public void stop() throws IOException
    {
        log.info("注册中心关闭");
        System.exit(0);
    }
}
