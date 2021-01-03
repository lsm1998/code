/*
 * 作者：刘时明
 * 时间：2020/3/8-16:13
 * 作用：
 */
package com.lsm1998.echoes.registry.config;

import com.lsm1998.echoes.common.net.EchoesServer;
import com.lsm1998.echoes.registry.enums.LoadStrategy;
import com.lsm1998.echoes.registry.enums.SerializeStrategy;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Data
@Component
@ConfigurationProperties(prefix = "echoes")
public class RegistryConfig
{
    public static RegistryConfig defaultConfig;

    // 超时时间
    private int timeOut;
    // 负载均衡策略
    private LoadStrategy loadStrategy;
    // 服务实现，BIO或者NIO
    private Class<? extends EchoesServer> serverClass;
    // 运行端口
    private int port;
    // 是否集群
    private boolean isCluster;
    // 集群地址
    private String[] clusterAdder;
    // 数据序列化策略
    private SerializeStrategy serializeStrategy;

    @PostConstruct
    public void initConfig()
    {
        if (defaultConfig != null)
        {
            BeanUtils.copyProperties(defaultConfig,this);
        }
        if (loadStrategy == null)
        {
            loadStrategy = LoadStrategy.POLL;
        }
        if (serializeStrategy == null)
        {
            serializeStrategy = SerializeStrategy.JDK;
        }
    }
}
