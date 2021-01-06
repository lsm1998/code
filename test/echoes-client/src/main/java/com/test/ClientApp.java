package com.test;

import com.lsm1998.echoes.registry.client.RegistryClient;
import com.lsm1998.echoes.rpc.client.ReferenceClient;
import com.lsm1998.echoes.rpc.client.ReferenceClientBuild;
import com.test.service.UserService;

public class ClientApp
{
    public static void main(String[] args) throws Exception
    {
        // 注册中心
        RegistryClient registry = new RegistryClient("127.0.0.1", 12221);

        // 创建ReferenceClient，每个服务单独一个
        ReferenceClient reference = new ReferenceClientBuild().
                registry(registry).
                serviceName("DemoService").build();

        // 获取代理对象
        UserService userService = reference.getProxy(UserService.class);

        // rpc调用
        String result = userService.hello("lsm");
        System.out.println(result);


        userService.sayHello();
    }
}
