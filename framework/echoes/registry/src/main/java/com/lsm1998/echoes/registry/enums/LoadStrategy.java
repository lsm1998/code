package com.lsm1998.echoes.registry.enums;

/**
 * @program: code
 * @description:
 * @author: lsm
 * @create: 2020-04-01 15:30
 **/
public enum LoadStrategy
{
    // 随机
    RANDOM,
    // 一致性哈希
    HASH,
    // 轮询
    POLL
}
