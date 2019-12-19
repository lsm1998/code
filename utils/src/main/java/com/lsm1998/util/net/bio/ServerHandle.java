package com.lsm1998.util.net.bio;

import com.lsm1998.util.net.bean.MsgData;

import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

/**
 * @作者：刘时明
 * @时间：2019/6/16-21:54
 * @作用：TCP服务端处理事件
 */
@FunctionalInterface
public interface ServerHandle
{
    void handle(MsgData data, ObjectOutputStream oos, List<Socket> list, Socket socket);
}
