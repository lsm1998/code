/*
 * 作者：刘时明
 * 时间：2019/12/22-21:05
 * 作用：
 */
package netty.queue;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * Netty线程模型是改良版的主从Reactor多线程模型
 */
public class NettyServer
{
    public static void main(String[] args) throws InterruptedException
    {
        // 创建BossGroup和WorkerGroup
        // 两个线程组，分别处理连接请求和业务请求
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try
        {
            // 启动对象，配置参数
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup,workerGroup)
                    // 通道实现使用NioServerSocketChannel
                    .channel(NioServerSocketChannel.class)
                    // 线程队列连接个数为128
                    .option(ChannelOption.SO_BACKLOG, 128)
                    // 保持活动连接状态
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childHandler(new ChannelInitializer<SocketChannel>()
                    {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception
                        {
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            pipeline.addLast(new NettyServerHandler());
                        }
                    });
            System.out.println("服务器准备就绪。。。");
            // 指定IP端口
            ChannelFuture future = bootstrap.bind(8000).sync();

            // 对关闭通道进行监听
            future.channel().closeFuture().sync();
        }finally
        {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
