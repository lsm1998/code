/*
 * 作者：刘时明
 * 时间：2020/4/18-22:39
 * 作用：
 */
package com.lsm1998.net.echoes.nio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class NIOHandler
{
    public static void handlerData(String content, SocketChannel dest) throws IOException
    {
        dest.write(ByteBuffer.wrap(content.getBytes()));
    }
}
