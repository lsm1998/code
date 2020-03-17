/*
 * 作者：刘时明
 * 时间：2020/3/17-21:15
 * 作用：
 */
package pb;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NettyProtoBufServer
{
    public static void main(String[] args) throws Exception
    {
        EventLoopGroup parentGroup = new NioEventLoopGroup(1);
        EventLoopGroup childGroup = new NioEventLoopGroup();
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(parentGroup, childGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 1024)
                // 连接超时
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 2000)
                .handler(new LoggingHandler(LogLevel.TRACE))
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) {
                        ch.pipeline()
                                .addLast(new ProtobufVarint32FrameDecoder())
                                .addLast(new ProtobufDecoder(TaskProtobufWrapper.TaskProtocol.getDefaultInstance()))
                                .addLast(new ProtobufVarint32LengthFieldPrepender())
                                .addLast(new ProtobufEncoder())
                                .addLast(new ServerProtoBufHandler());
                    }
                });
        // 绑定端口，同步等待成功
        ChannelFuture future = bootstrap.bind(9090).sync();
        log.info("server start in port:[{}]", 9090);
        // 等待服务端链路关闭后,main线程退出
        future.channel().closeFuture().sync();
        // 关闭线程池资源
        parentGroup.shutdownGracefully();
        childGroup.shutdownGracefully();
    }
}
