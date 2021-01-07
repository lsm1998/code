package com.lsm1998.echoes.rpc.client;

import com.lsm1998.echoes.common.net.RpcCallRequest;
import com.lsm1998.echoes.common.net.RpcCallResponse;
import com.lsm1998.echoes.common.utils.BitObjectUtil;
import com.lsm1998.echoes.registry.bean.RegistryNodeBean;
import com.lsm1998.echoes.registry.client.RegistryClient;
import com.lsm1998.echoes.rpc.EchoesRpcClient;
import com.lsm1998.echoes.rpc.invoke.ProxyFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Optional;

public class DefaultReferenceClient implements ReferenceClient, EchoesRpcClient
{
    private final RegistryClient registry;

    private final String serviceName;

    private Selector selector;
    private SocketChannel channel;
    private final ByteBuffer buffer = ByteBuffer.allocate(1024);

    protected DefaultReferenceClient(String serviceName, RegistryClient registry)
    {
        this.registry = registry;
        this.serviceName = serviceName;
        this.init();
    }

    private void init()
    {
        RegistryNodeBean node = registry.get(serviceName);
        if (node == null)
        {
            throw new RuntimeException("连接注册中心失败");
        }
        try
        {
            this.connect(node.getIp(), node.getPort());
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public <E> E getProxy(Class<E> clazz) throws IOException
    {
        if (!clazz.isInterface())
        {
            throw new RuntimeException("只会为接口类型生生代理类！");
        }
        return ProxyFactory.getProxy(clazz, this);
    }

    @Override
    public void connect(String address, int port) throws IOException
    {
        try
        {
            selector = Selector.open();
            InetSocketAddress isa = new InetSocketAddress(address, port);
            channel = SocketChannel.open(isa);
            channel.configureBlocking(false);
            channel.register(selector, SelectionKey.OP_READ);
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void close() throws IOException
    {
        channel.close();
        selector.close();
    }

    @Override
    public RpcCallResponse rpcCall(String className, String methodName, Object[] args, Class<?>[] types) throws IOException
    {
        Optional<byte[]> optional = BitObjectUtil.objectToBytes(RpcCallRequest.of(className, methodName, args, types));
        if (optional.isPresent())
        {
            channel.write(ByteBuffer.wrap(optional.get()));
        }
        return getResult();
    }

    public RpcCallResponse getResult() throws IOException
    {
        while (selector.select() > 0)
        {
            for (SelectionKey key : selector.selectedKeys())
            {
                selector.selectedKeys().remove(key);
                if (key.isReadable())
                {
                    SocketChannel channel = (SocketChannel) key.channel();
                    buffer.clear();
                    int len;
                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    while ((len = channel.read(buffer)) > 0)
                    {
                        buffer.flip();
                        outputStream.write(buffer.array(), 0, len);
                    }
                    buffer.clear();
                    byte[] bytes = outputStream.toByteArray();
                    if (bytes.length == 0)
                    {
                        return RpcCallResponse.of(null);
                    }
                    Optional<RpcCallResponse> optional = BitObjectUtil.bytesToObject(bytes);
                    return optional.orElse(null);
                }
            }
        }
        return null;
    }
}
