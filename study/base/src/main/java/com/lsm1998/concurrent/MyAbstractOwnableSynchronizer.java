/*
 * 作者：刘时明
 * 时间：2020/5/2-11:54
 * 作用：
 */
package com.lsm1998.concurrent;

import java.io.Serializable;

public abstract class MyAbstractOwnableSynchronizer implements Serializable
{
    // 独占模式中当前占有的线程
    private transient Thread exclusiveOwnerThread;

    protected MyAbstractOwnableSynchronizer(){}

    public final Thread getExclusiveOwnerThread()
    {
        return exclusiveOwnerThread;
    }

    public final void setExclusiveOwnerThread(Thread exclusiveOwnerThread)
    {
        this.exclusiveOwnerThread = exclusiveOwnerThread;
    }
}
