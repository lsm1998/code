# echoes

自定义实现的RPC框架，类似于dubbo

* echoes-common 通用工具
* echoes-config 配置相关（没用起来，就2个类，名存实亡）
* echoes-registry 基于SpringBoot的注册中心（HTTP协议简单好用）
* echoes-rpc 基于NIO的RPC服务端和客户端实现
* echoes-test 原本打算存放测试用例

使用demo参见：
* [api demo](https://github.com/lsm1998/code/tree/master/test/echoes-api)
* [客户端demo](https://github.com/lsm1998/code/tree/master/test/echoes-client)
* [服务端demo](https://github.com/lsm1998/code/tree/master/test/echoes-server)
* [注册中心demo](https://github.com/lsm1998/code/tree/master/test/registry)

分别启动注册中心、服务端、客户端即可，暂时没有实现客户端和服务端之间的断线重连
