/*
 * 作者：刘时明
 * 时间：2020/3/8-15:08
 * 作用：注册中心存储的数据
 */
package com.lsm1998.echoes.registry.bean;

public interface ReportData
{
    /**
     * 获取类型
     *
     * @return
     */
    String getType();

    /**
     * 获取IP地址
     *
     * @return
     */
    String getAddress();

    /**
     * 获取端口
     *
     * @return
     */
    int getPort();

    /**
     * 获取唯一标识
     *
     * @return
     */
    String getId();
}
