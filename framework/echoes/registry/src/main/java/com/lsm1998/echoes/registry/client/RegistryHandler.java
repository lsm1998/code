/*
 * 作者：刘时明
 * 时间：2020/3/8-18:11
 * 作用：
 */
package com.lsm1998.echoes.registry.client;

import com.lsm1998.echoes.registry.bean.AppReport;
import com.lsm1998.echoes.registry.bean.MethodReport;

import java.io.IOException;
import java.util.List;

public interface RegistryHandler
{
    /**
     * 注册App
     */
    void registryApp(String serviceName, String address, int port) throws IOException;

    /**
     * 注册方法
     *
     * @param serviceName
     * @param generic
     */
    void registryMethod(String serviceName, String generic, String clazz) throws IOException;

    /**
     * App列表
     *
     * @param serviceName
     * @return
     */
    List<AppReport> queryApp(String serviceName);

    /**
     * 方法列表
     *
     * @param serviceName
     * @return
     */
    List<MethodReport> queryMethod(String serviceName);

    AppReport getApp(String serviceName);

    MethodReport getMethod(String methodName);
}
