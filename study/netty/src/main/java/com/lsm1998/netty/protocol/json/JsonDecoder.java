package com.lsm1998.netty.protocol.json;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @program: code
 * @description:
 * @author: lsm
 * @create: 2020-04-28 11:55
 **/
public class JsonDecoder extends ByteToMessageDecoder
{
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf byteBuf, List<Object> list) throws Exception
    {

    }
}
