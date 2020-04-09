/*
 * 作者：刘时明
 * 时间：2019/12/22-21:22
 * 作用：
 */
package netty.base;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * 继承Netty规定的Handler适配器
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter
{
    // 读取数据
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception
    {
        System.out.println("处理线程=" + Thread.currentThread().getName());
        ByteBuf buf = (ByteBuf) msg;
        System.out.println("客户端发送的消息=" + buf.toString(CharsetUtil.UTF_8));
    }

    // 数据读取完后调用
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception
    {
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello,客户端", CharsetUtil.UTF_8));
    }

    // 发送异常调用
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception
    {
        System.out.println("一个客户端退出连接");
        ctx.close();
    }
}
