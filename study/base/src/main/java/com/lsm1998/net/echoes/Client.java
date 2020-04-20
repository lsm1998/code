/*
 * 作者：刘时明
 * 时间：2020/4/19-22:56
 * 作用：
 */
package com.lsm1998.net.echoes;

public interface Client
{
    void connect();

    void send(String msg);

    void stop();
}
