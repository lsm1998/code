/*
 * 作者：刘时明
 * 时间：2020/3/8-17:35
 * 作用：
 */
package com.lsm1998.echoes.registry.service.impl;

import com.google.gson.Gson;
import com.lsm1998.echoes.registry.bean.AppReport;
import com.lsm1998.echoes.registry.bean.MethodReport;
import com.lsm1998.echoes.registry.config.RegistryConfig;
import com.lsm1998.echoes.registry.serialize.ReqData;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class RegistryHandler
{
    private RegistryConfig config;
    private static final ReadWriteLock LOCK = new ReentrantReadWriteLock();
    private static final Gson gson = new Gson();
    private static final Map<String, List<AppReport>> serviceMap = new HashMap<>();
    private static final Map<String, List<MethodReport>> methodMap = new HashMap<>();
    private static final byte[] SUCCESS;
    private static final byte[] ERROR;

    public RegistryHandler(RegistryConfig config)
    {
        this.config = config;
    }

    static
    {
        SUCCESS = "{\"code\":1}".getBytes();
        ERROR = "{\"code\":0}".getBytes();
    }

    public void handlerData(String jsonStr, SocketChannel dest) throws IOException
    {
        ReqData reqData = gson.fromJson(jsonStr, ReqData.class);
        switch (reqData.getCode())
        {
            case 1:
                dest.write(ByteBuffer.wrap(handler1(reqData.getData())));
                break;
            case 2:
                dest.write(ByteBuffer.wrap(handler2(reqData.getData())));
                break;
            case 3:
                dest.write(ByteBuffer.wrap(handler3(reqData.getData())));
                break;
            case 4:
                dest.write(ByteBuffer.wrap(handler4(reqData.getData())));
                break;
            case 5:
                dest.write(ByteBuffer.wrap(handler5(reqData.getData())));
                break;
        }
    }

    private byte[] handler5(String data)
    {
        List<AppReport> list = serviceMap.get(data);
        int size = list == null ? 0 : list.size();
        if (size == 0)
        {
            return new byte[]{};
        } else if (list.size() == 1)
        {
            return gson.toJson(list.get(0)).getBytes();
        } else
        {
            if (config.getLoadStrategy() == 1)
            {

            }
            return null;
        }
    }

    private byte[] handler3(String data)
    {
        List<AppReport> list = serviceMap.get(data);
        if (list == null)
        {
            return new byte[]{};
        } else
        {
            return gson.toJson(list).getBytes();
        }
    }

    private byte[] handler4(String data)
    {
        List<MethodReport> list = methodMap.get(data);
        if (list == null)
        {
            return new byte[]{};
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
            if (serviceMap.containsKey(arr[0]))
            {
                list = serviceMap.get(arr[0]);
            } else
            {
                list = new ArrayList<>();
            }
            list.add(report);
            serviceMap.put(arr[0], list);
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
            if (methodMap.containsKey(arr[0]))
            {
                list = methodMap.get(arr[0]);
            } else
            {
                list = new ArrayList<>();
            }
            list.add(methodReport);
            methodMap.put(arr[0], list);
        } finally
        {
            LOCK.writeLock().unlock();
        }
        return SUCCESS;
    }
}
