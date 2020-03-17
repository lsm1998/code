/*
 * 作者：刘时明
 * 时间：2020/3/8-16:26
 * 作用：
 */
package com.lsm1998.echoes.common.net;

import java.io.IOException;

public interface EchoesServer
{
    void start(int port) throws IOException;

    void stop() throws IOException;
}
