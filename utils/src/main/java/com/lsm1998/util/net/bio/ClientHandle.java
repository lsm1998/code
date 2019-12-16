package com.lsm1998.util.net.bio;

import com.lsm1998.util.net.bean.MsgData;

/**
 * @作者：刘时明
 * @时间：2019/6/16-22:28
 * @作用：
 */
@FunctionalInterface
public interface ClientHandle
{
    void accept(MsgData data);
}
