/*
 * 作者：刘时明
 * 时间：2020/3/8-16:26
 * 作用：
 */
package com.lsm1998.net.echoes.common.net;

import java.io.IOException;

public interface EchoesClient
{
    void connect(String address,int port) throws IOException;

    void close() throws IOException;
}
