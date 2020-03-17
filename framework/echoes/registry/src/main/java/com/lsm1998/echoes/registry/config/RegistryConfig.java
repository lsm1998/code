/*
 * 作者：刘时明
 * 时间：2020/3/8-16:13
 * 作用：
 */
package com.lsm1998.echoes.registry.config;

import com.lsm1998.echoes.common.net.EchoesServer;
import lombok.Data;

@Data
public class RegistryConfig
{
    // 超时时间
    private int timeOut;
    // 负载均衡粗略，1轮询，2随机
    private int loadStrategy;
    // 服务实现，BIO或者NIO
    private Class<? extends EchoesServer> serverClass;
}
