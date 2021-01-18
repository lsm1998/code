package com.lsm1998.echoes.rpc.context;

import com.lsm1998.echoes.config.EchoesConfig;
import com.lsm1998.echoes.rpc.bean.ProxyBean;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: code
 * @description:
 * @author: lsm
 * @create: 2020-04-01 18:26
 **/
@Slf4j
public class DefaultEchoesService extends AbstractEchoesService
{
    private final EchoesConfig echoesConfig;
    private final Map<String, ProxyBean<?>> targetObjMap;
    private Selector selector;
    private final DefaultEchoesHandler handler;

    protected DefaultEchoesService(EchoesConfig echoesConfig)
    {
        this.echoesConfig = echoesConfig;
        this.targetObjMap = new HashMap<>(16);
        this.handler = new DefaultEchoesHandler(this.echoesConfig.getRpc(), this.targetObjMap);
    }

    @Override
    public void export() throws IOException
    {
        // 1.注册RPC代理类
        classProxy();

        // 2.连接配置中心
        connectRegistry(echoesConfig.getRpc().getServiceName());

        // 3.启动服务
        startService();
    }

    @Override
    void startService() throws IOException
    {
        selector = Selector.open();
        this.initServer(this.echoesConfig.getRpc().getPort());
    }

    @Override
    void classProxy()
    {
        RpcProxy rpcProxy = new RpcProxy(this.echoesConfig.getRpc());
        rpcProxy.classProxy(targetObjMap);
        log.info("RPC代理类注册完成");
    }

    @Override
    void connectRegistry(String serviceName)
    {
        EchoesConfig.Rpc rpc = this.echoesConfig.getRpc();
        EchoesConfig.Registry registry = this.echoesConfig.getRegistry();
        RegistryHandler handler = new RegistryHandler();
        try
        {
            handler.connect(registry.getAdder(), registry.getPort());
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        handler.report(serviceName, "127.0.0.1", rpc.getPort(), targetObjMap);
        log.info("注册中心连接完毕");
    }

    private void initServer(int port) throws IOException
    {
        InetSocketAddress isa = new InetSocketAddress("127.0.0.1", port);
        ServerSocketChannel server = ServerSocketChannel.open();
        server.bind(isa);
        // 设置非阻塞
        server.configureBlocking(false);
        server.register(selector, SelectionKey.OP_ACCEPT);
        log.info("rpc server start,port=[{}]", port);
        while (selector.select() > 0)
        {
            for (SelectionKey key : selector.selectedKeys())
            {
                selector.selectedKeys().remove(key);
                // 是否包含客户端请求
                if (key.isAcceptable())
                {
                    handler.acceptable(key, server, selector);
                }
                // 是否存在读取的数据
                if (key.isReadable())
                {
                    handler.readable(key);
                }
            }
        }
    }
}
