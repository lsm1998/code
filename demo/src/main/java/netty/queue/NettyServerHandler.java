/*
 * 作者：刘时明
 * 时间：2019/12/22-21:22
 * 作用：
 */
package netty.queue;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

import java.util.concurrent.TimeUnit;

/**
 * 继承Netty规定的Handler适配器
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter
{
    // 读取数据
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception
    {
        // 模拟耗时任务，需要提交异步队列
        System.out.println("文件上传开始");
        //Thread.sleep(10 * 1000);
        //System.out.println("文件上传完毕！！！");

        // 对于异步队列来说，同一个通道处理线程只有一个
        // 解决方案一，用户自定义普通任务，taskQueue
        ctx.channel().eventLoop().execute(() ->
        {
            try
            {
                Thread.sleep(10 * 1000);
                ctx.writeAndFlush(Unpooled.copiedBuffer("服务器已经接受了你的文件", CharsetUtil.UTF_8));
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        });
        // 解决方案二，用户自定义定时任务，scheduleTaskQueue
        ctx.channel().eventLoop().schedule(() ->
        {
            ctx.writeAndFlush(Unpooled.copiedBuffer("服务器等待了5秒，执行了你的任务", CharsetUtil.UTF_8));
        }, 5, TimeUnit.SECONDS);
        System.out.println("文件上传完毕！！！");
    }

    // 数据读取完后调用
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception
    {
        ctx.writeAndFlush(Unpooled.copiedBuffer("服务器已经收到!!!", CharsetUtil.UTF_8));
    }

    // 发送异常调用
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception
    {
        System.out.println("一个客户端退出连接");
        ctx.close();
    }
}
