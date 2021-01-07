package com.lsm1998.echoes.rpc.context;

import com.lsm1998.echoes.common.net.NullVal;
import com.lsm1998.echoes.common.net.RpcCallRequest;
import com.lsm1998.echoes.common.net.RpcCallResponse;
import com.lsm1998.echoes.common.utils.BitObjectUtil;
import com.lsm1998.echoes.config.EchoesConfig;
import com.lsm1998.echoes.rpc.bean.ProxyBean;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.rmi.RemoteException;
import java.util.Map;
import java.util.Optional;

public class DefaultEchoesHandler
{
    private EchoesConfig.Rpc config;

    private Map<String, ProxyBean<?>> targetObjMap;

    public DefaultEchoesHandler(EchoesConfig.Rpc config, Map<String, ProxyBean<?>> targetObjMap)
    {
        this.config = config;
        this.targetObjMap = targetObjMap;
    }

    /**
     * 连接加入
     *
     * @param key
     * @param server
     * @param selector
     * @throws IOException
     */
    public void acceptable(SelectionKey key, ServerSocketChannel server, Selector selector) throws IOException
    {
        SocketChannel channel = server.accept();
        channel.configureBlocking(false);
        channel.register(selector, SelectionKey.OP_READ);
        key.interestOps(SelectionKey.OP_ACCEPT);
    }

    /**
     * 数据可读
     *
     * @param key
     * @throws IOException
     */
    public void readable(SelectionKey key) throws IOException
    {
        SocketChannel channel = (SocketChannel) key.channel();
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try
        {
            int len;
            while ((len = channel.read(buffer)) > 0)
            {
                buffer.flip();
                outputStream.write(buffer.array(), 0, len);
            }
            if (len == -1)
            {
                channelClose(key);
                return;
            }
            // 准备下一次读取
            key.interestOps(SelectionKey.OP_READ);
        } catch (Exception e)
        {
            channelClose(key);
        }
        byte[] byteArray = outputStream.toByteArray();
        if (byteArray.length == 0)
        {
            return;
        }
        RpcCallResponse result = null;
        Optional<RpcCallRequest> optional = BitObjectUtil.bytesToObject(byteArray);
        if (optional.isPresent())
        {
            RpcCallRequest request = optional.get();
            checkRequest(request);
            ProxyBean<?> proxyBean = this.targetObjMap.get(request.getClassName());
            if (proxyBean == null)
            {
                throw new RemoteException("找不到对应的类型，" + request.getClassName());
            }
            Object target = proxyBean.getTarget();
            try
            {
                Method method = target.getClass().getMethod(request.getMethodName(), request.getTypes());
                Object obj = method.invoke(target, request.getArgs());
                result = RpcCallResponse.of(obj == null ? NullVal.Instance : obj);
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        Optional<byte[]> bytes = BitObjectUtil.objectToBytes(result);
        channel.write(ByteBuffer.wrap(bytes.orElse(new byte[]{})));
    }

    private void checkRequest(RpcCallRequest request)
    {
        if (request == null)
        {
            throw new RuntimeException("RpcCallRequest对象不可以为空！");
        }
        if (request.getMethodName() == null || request.getClassName() == null)
        {
            throw new RuntimeException("RpcCallRequest对象关键属性不可以为空！");
        }
    }

    private void channelClose(SelectionKey key) throws IOException
    {
        // 从Selector删除数据
        key.cancel();
        if (key.channel() != null)
        {
            key.channel().close();
        }
    }
}
