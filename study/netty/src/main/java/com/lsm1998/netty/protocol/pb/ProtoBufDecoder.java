package com.lsm1998.netty.protocol.pb;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @program: code
 * @description:
 * @author: lsm
 * @create: 2020-04-28 11:54
 **/
public class ProtoBufDecoder extends ByteToMessageDecoder
{
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf byteBuf, List<Object> list) throws Exception
    {
        try
        {
            //先获取可读字节数
            final int length = byteBuf.readableBytes();
            final byte[] array = new byte[length];
            byteBuf.getBytes(byteBuf.readerIndex(), array, 0, length);
            // 使用protobuf字节数组转对象方法
            // list.add();
            byteBuf.clear();
        }catch (Exception e)
        {
            // 解析失败视为非法消息
            ctx.channel().pipeline().remove(this);
        }
    }
}
