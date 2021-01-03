package com.lsm1998.echoes.rpc.context;

import com.lsm1998.echoes.common.annotaion.EchoesRpcServer;
import com.lsm1998.echoes.config.EchoesConfig;
import com.lsm1998.echoes.rpc.bean.ProxyBean;
import com.lsm1998.echoes.rpc.proxy.ProxyInstanceFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @program: code
 * @description:
 * @author: lsm
 * @create: 2020-04-13 11:02
 **/
public class RpcProxy
{
    private EchoesConfig.Rpc rpc;

    public RpcProxy(EchoesConfig.Rpc rpc)
    {
        this.rpc = rpc;
    }

    private List<Class<?>> classList()
    {
        String packagePath = rpc.getScanPackage();
        String packageDirName = packagePath.replace('.', '/');
        System.out.println(packageDirName);
        File file = new File(this.getClass().getResource("/").getPath() + packageDirName);
        File[] files = file.listFiles();
        assert files != null;
        List<Class<?>> proxyClassList = new ArrayList<>();
        try
        {
            for (File f : files)
            {
                int len = f.getName().length();
                proxyClassList.add(Class.forName(packagePath + "." + f.getName().substring(0, len - 6)));
            }
        } catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        return proxyClassList;
    }

    public void classProxy(Map<String, ProxyBean<?>> targetObjMap)
    {
        // 获取packagePath里的类
        List<Class<?>> classList = this.classList();
        try
        {
            for (Class<?> c : classList)
            {
                EchoesRpcServer echoesRpcServer = c.getAnnotation(EchoesRpcServer.class);
                if (echoesRpcServer != null)
                {
                    Object obj = c.getConstructor().newInstance();
                    targetObjMap.put(c.getName(), ProxyBean.of(echoesRpcServer, obj, ProxyInstanceFactory.getInstance(obj)));
                }
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
