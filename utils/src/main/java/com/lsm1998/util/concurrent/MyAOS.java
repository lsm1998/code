package com.lsm1998.util.concurrent;

import lombok.Data;

import java.io.Serializable;

/**
 * 自己实现的AbstractOwnableSynchronizer（抽象可拥有同步器）
 */
@Data
public abstract class MyAOS implements Serializable
{
    protected MyAOS()
    {
    }

    // 独占模式同步的当前所有者
    private transient Thread exclusiveOwnerThread;
}
