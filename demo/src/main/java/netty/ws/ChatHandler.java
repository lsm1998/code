/*
 * 作者：刘时明
 * 时间：2019/12/29-16:48
 * 作用：
 */
package netty.ws;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

// TextWebSocketFrame ws文本消息
@Slf4j
public class ChatHandler extends SimpleChannelInboundHandler<TextWebSocketFrame>
{
    // 记录管理所有的客户端
    static ChannelGroup clients = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, TextWebSocketFrame textWebSocketFrame) throws Exception
    {
        String message = textWebSocketFrame.text();
        log.info("接受的消息={}", message);
        message = "[消息转发]=" + message + " time=" + new Date();
//        for (Channel c : clients)
//        {
//            c.writeAndFlush(new TextWebSocketFrame(message));
//        }
        clients.writeAndFlush(new TextWebSocketFrame(message));
    }

    // 用户加入
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception
    {
        clients.add(ctx.channel());
        log.info("一个用户加入，当前={}", clients.size());
    }

    // 用户离开
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception
    {
        // clients.remove(ctx.channel());
        log.info("一个用户离开，当前={}，id={}", clients.size(), ctx.channel().id().asShortText());
    }
}
