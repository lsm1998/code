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
public class NettyClientHandler extends ChannelInboundHandlerAdapter
{
    // 通道就绪时调用
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception
    {
        System.out.println("client=" + ctx);
        ctx.writeAndFlush(Unpooled.copiedBuffer("哈哈哈", CharsetUtil.UTF_8));
    }

    // 读取数据
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception
    {
        ByteBuf buf = (ByteBuf) msg;
        System.out.println("服务端发送的消息=" + buf.toString(CharsetUtil.UTF_8));
        System.out.println("服务端地址=" + ctx.channel().remoteAddress());
    }

    // 发送异常调用
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception
    {
        cause.printStackTrace();
        ctx.close();
    }
}
