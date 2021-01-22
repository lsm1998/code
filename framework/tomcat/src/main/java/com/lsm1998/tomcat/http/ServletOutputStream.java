package com.lsm1998.tomcat.http;

import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;

import java.io.IOException;
import java.io.OutputStream;

/**
 * @program: code
 * @description:
 * @author: lsm
 * @create: 2020-04-09 11:32
 **/
public class ServletOutputStream extends OutputStream
{
    private HttpServletResponse resp;

    public ServletOutputStream(HttpServletResponse response)
    {
        this.resp = response;
    }

    @Override
    public void write(int i) throws IOException
    {

    }

    public void write(String content) throws IOException
    {
        try
        {
            if (content == null || content.length() == 0)
            {
                return;
            }
            // 设置 http协议及请求头信息
            FullHttpResponse response = new DefaultFullHttpResponse(
                    // 设置http版本为1.1
                    HttpVersion.HTTP_1_1,
                    // 设置响应状态码
                    HttpResponseStatus.OK,
                    // 将输出值写出 编码为UTF-8
                    Unpooled.wrappedBuffer(content.getBytes("UTF-8")));
            response.headers().set("Content-Type", resp.contentType);
            resp.ctx.write(response);
        } finally
        {
            resp.ctx.flush();
            resp.ctx.close();
        }
    }

    public void writeFile(byte[] bytes) throws IOException
    {
        try
        {
            if (bytes == null || bytes.length == 0)
            {
                return;
            }
            // 设置 http协议及请求头信息
            FullHttpResponse response = new DefaultFullHttpResponse(
                    // 设置http版本为1.1
                    HttpVersion.HTTP_1_1,
                    // 设置响应状态码
                    HttpResponseStatus.OK,
                    // 将输出值写出 编码为UTF-8
                    Unpooled.wrappedBuffer(bytes));
            response.headers().set("Content-Type", resp.contentType);
            resp.ctx.write(response);
        } finally
        {
            resp.ctx.flush();
            resp.ctx.close();
        }
    }
}
