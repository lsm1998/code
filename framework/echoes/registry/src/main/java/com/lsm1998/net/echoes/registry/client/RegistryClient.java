/*
 * 作者：刘时明
 * 时间：2020/3/8-14:57
 * 作用：注册中心客户端程序
 */
package com.lsm1998.net.echoes.registry.client;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lsm1998.net.echoes.registry.bean.AppReport;
import com.lsm1998.net.echoes.registry.bean.MethodReport;
import com.lsm1998.net.echoes.registry.serialize.ReqData;
import com.lsm1998.net.echoes.registry.Define;

import java.io.IOException;
import java.util.List;

public class RegistryClient extends DefClient implements RegistryHandler
{
    private final Gson gson = new Gson();

    public RegistryClient() throws IOException
    {
        super("127.0.0.1", Define.PORT);
    }

    public RegistryClient(int port) throws IOException
    {
        super("127.0.0.1", port);
    }

    public RegistryClient(String address, int port) throws IOException
    {
        super(address, port);
    }

    @Override
    public void registryApp(String serviceName, String address, int port)
    {
        ReqData req = new ReqData();
        req.setCode(1);
        req.setData(String.format("%s$%s$%s", serviceName, address, port));
        this.client2Server(gson.toJson(req));
    }

    @Override
    public void registryMethod(String serviceName, String generic, String clazz) throws IOException
    {
        ReqData req = new ReqData();
        req.setCode(2);
        req.setData(String.format("%s$%s$%s", serviceName, generic, clazz));
        this.client2Server(gson.toJson(req));
    }

    @Override
    public List<AppReport> queryApp(String serviceName)
    {
        ReqData req = new ReqData();
        req.setCode(3);
        req.setData(serviceName);
        String jsonStr = this.client2Server(gson.toJson(req));
        return gson.fromJson(jsonStr, new TypeToken<List<AppReport>>() {}.getType());
    }

    @Override
    public List<MethodReport> queryMethod(String serviceName)
    {
        ReqData req = new ReqData();
        req.setCode(4);
        req.setData(serviceName);
        String jsonStr = this.client2Server(gson.toJson(req));
        return gson.fromJson(jsonStr, List.class);
    }

    @Override
    public AppReport getApp(String serviceName)
    {
        ReqData req = new ReqData();
        req.setCode(5);
        req.setData(serviceName);
        String jsonStr = this.client2Server(gson.toJson(req));
        return gson.fromJson(jsonStr, AppReport.class);
    }

    @Override
    public MethodReport getMethod(String methodName)
    {
        ReqData req = new ReqData();
        req.setCode(6);
        req.setData(methodName);
        String jsonStr = this.client2Server(gson.toJson(req));
        return gson.fromJson(jsonStr, MethodReport.class);
    }
}
