/*
 * 作者：刘时明
 * 时间：2019/12/29-16:29
 * 作用：
 */
package netty.ws;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class WSServer
{
    public static void main(String[] args) throws Exception
    {
        EventLoopGroup mainGroup = new NioEventLoopGroup();
        EventLoopGroup subGroup = new NioEventLoopGroup();
        try
        {
            ServerBootstrap server = new ServerBootstrap();
            server.group(mainGroup, subGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new WSServerInit());
            ChannelFuture future = server.bind(8000).sync();
            future.channel().closeFuture().sync();
        } finally
        {
            mainGroup.shutdownGracefully();
            subGroup.shutdownGracefully();
        }
    }
}
