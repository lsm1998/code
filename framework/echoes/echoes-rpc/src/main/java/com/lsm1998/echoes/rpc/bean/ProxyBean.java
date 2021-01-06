package com.lsm1998.echoes.rpc.bean;

import com.lsm1998.echoes.common.annotaion.EchoesRpcServer;
import lombok.Data;

/**
 * @program: code
 * @description:
 * @author: lsm
 * @create: 2020-04-13 11:14
 **/
@Data
public class ProxyBean<T>
{
    private EchoesRpcServer echoesRpcServer;
    private T source;
    private T target;

    public static <T> ProxyBean<T> of(EchoesRpcServer echoesRpcServer, T source, T target)
    {
        ProxyBean<T> bean = new ProxyBean<>();
        bean.source = source;
        bean.target = target;
        bean.echoesRpcServer = echoesRpcServer;
        return bean;
    }
}
