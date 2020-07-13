package com.lsm1998.echoes.rpc.protocol;

import lombok.Data;

import java.io.Serializable;

/**
 * @program: code
 * @description:
 * @author: lsm
 * @create: 2020-04-13 10:46
 **/
@Data
public class InvokerProtocol implements Serializable
{
    // 服务名称
    private String serviceName;
    // 函数名称
    private String methodName;
    // 参数列表
    private Object[] values;
}
