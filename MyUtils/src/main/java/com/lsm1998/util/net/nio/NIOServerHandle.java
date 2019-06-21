package com.lsm1998.util.net.nio;

import com.lsm1998.util.net.bean.MsgData;

import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.Set;

/**
 * @作者：刘时明
 * @时间：2019/6/17-9:21
 * @作用：
 */
@FunctionalInterface
public interface NIOServerHandle
{
    void handle(MsgData data, SocketChannel dest, Set<SelectionKey> keys);
}
