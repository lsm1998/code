package com.lsm1998.echoes.rpc;

import java.io.IOException;

/**
 * @program: code
 * @description:
 * @author: lsm
 * @create: 2020-04-01 18:13
 **/
public interface EchoesService
{
    // 暴露及注册服务
    void export() throws IOException;
}
