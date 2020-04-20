/*
 * 作者：刘时明
 * 时间：2020/3/8-17:35
 * 作用：
 */
package com.lsm1998.net.echoes.registry.service.impl;

import com.google.gson.Gson;
import com.lsm1998.net.echoes.registry.bean.AppReport;
import com.lsm1998.net.echoes.registry.bean.MethodReport;
import com.lsm1998.net.echoes.registry.config.RegistryConfig;
import com.lsm1998.net.echoes.registry.enums.LoadStrategy;
import com.lsm1998.net.echoes.registry.serialize.ReqData;
import com.lsm1998.net.echoes.registry.service.RegistryContext;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class RegistryHandler
{
    private static final Gson gson = new Gson();
    private static final ReadWriteLock LOCK = new ReentrantReadWriteLock();
    private final RegistryContext context;

    private static final byte[] SUCCESS;
    private static final byte[] ERROR;
    private static final byte[] EMPTY;

    private RegistryConfig config;

    public RegistryHandler(RegistryConfig config)
    {
        this.config = config;
        this.context=RegistryContext.getInstance();
    }

    static
    {
        SUCCESS = "{\"code\":1}".getBytes();
        ERROR = "{\"code\":0}".getBytes();
        EMPTY= new byte[0];
    }

    public void handlerData(String jsonStr, SocketChannel dest) throws IOException
    {
        ReqData reqData = gson.fromJson(jsonStr, ReqData.class);
        byte[] result=null;
        switch (reqData.getCode())
        {
            case 1:
                result=handler1(reqData.getData());
                break;
            case 2:
                result=handler2(reqData.getData());
                break;
            case 3:
                result=handler3(reqData.getData());
                break;
            case 4:
                result=handler4(reqData.getData());
                break;
            case 5:
                result=handler5(reqData.getData());
                break;
        }
        if(result==null)result=new byte[]{};
        dest.write(ByteBuffer.wrap(result,0,result.length));
    }

    private byte[] handler5(String data)
    {
        List<AppReport> list = context.getServiceMap(data);
        int size = list == null ? 0 : list.size();
        if (size == 0)
        {
            return EMPTY;
        } else if (list.size() == 1)
        {
            return gson.toJson(list.get(0)).getBytes();
        } else
        {
            if (config.getLoadStrategy() == LoadStrategy.RANDOM)
            {

            }
            return EMPTY;
        }
    }

    private byte[] handler3(String data)
    {
        List<AppReport> list = context.getServiceMap(data);
        if (list == null)
        {
            return EMPTY;
        } else
        {
            return gson.toJson(list).getBytes();
        }
    }

    private byte[] handler4(String data)
    {
        List<MethodReport> list = context.getMethodMap(data);
        if (list == null)
        {
            return EMPTY;
        } else
        {
            return gson.toJson(list).getBytes();
        }
    }

    private byte[] handler1(String data)
    {
        String[] arr = data.split("[$]");
        AppReport report = new AppReport(arr[1], Integer.parseInt(arr[2]));
        List<AppReport> list;
        try
        {
            LOCK.writeLock().lock();
            if (context.serviceMapContainsKey(arr[0]))
            {
                list =  context.getServiceMap(arr[0]);
            } else
            {
                list = new ArrayList<>();
            }
            list.add(report);
            context.putServiceMap(arr[0], list);
        } finally
        {
            LOCK.writeLock().unlock();
        }
        return SUCCESS;
    }

    private byte[] handler2(String data)
    {
        String[] arr = data.split("[$]");
        MethodReport methodReport = new MethodReport(arr[1], arr[2]);
        List<MethodReport> list;
        try
        {
            LOCK.writeLock().lock();
            if (context.methodMapContainsKey(arr[0]))
            {
                list = context.getMethodMap(arr[0]);
            } else
            {
                list = new ArrayList<>();
            }
            list.add(methodReport);
            context.putMethodMap(arr[0], list);
        } finally
        {
            LOCK.writeLock().unlock();
        }
        return SUCCESS;
    }
}
