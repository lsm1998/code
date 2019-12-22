/*
 * 作者：刘时明
 * 时间：2019/12/22-21:35
 * 作用：
 */
package netty.base;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

public class NettyClient
{
    public static void main(String[] args) throws InterruptedException
    {
        NioEventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        try
        {
            // 客户端启动对象
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(eventLoopGroup)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>()
                    {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception
                        {
                            socketChannel.pipeline().addLast(new NettyClientHandler());
                        }
                    });
            System.out.println("客户端准备就绪。。。");

            ChannelFuture future = bootstrap.connect(new InetSocketAddress("127.0.0.1", 8000)).sync();
            future.channel().closeFuture().sync();
        } finally
        {
            eventLoopGroup.shutdownGracefully();
        }
    }
}
