/*
 * 作者：刘时明
 * 时间：2019/12/29-16:36
 * 作用：
 */
package netty.ws;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

public class WSServerInit extends ChannelInitializer<SocketChannel>
{
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception
    {
        ChannelPipeline pipeline = socketChannel.pipeline();
        // WS协议基于HTTP，需要HTTP解码器
        pipeline.addLast(new HttpServerCodec());
        // 对写大数据流的支持
        pipeline.addLast(new ChunkedWriteHandler());
        // 对HttpMessage聚合，聚合为FullHttpReq和FullHttpRsp
        pipeline.addLast(new HttpObjectAggregator(1024*64));

        // ws服务器处理的协议，指定路由，处理ws握手，ping+pong=心跳
        // ws协议数据以frames传输，不同的数据类型对应不同的frames
        pipeline.addLast(new WebSocketServerProtocolHandler("/chat"));

        // 自定义handler
        pipeline.addLast(new ChatHandler());
    }
}
