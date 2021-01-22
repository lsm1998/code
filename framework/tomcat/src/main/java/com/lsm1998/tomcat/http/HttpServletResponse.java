package com.lsm1998.tomcat.http;

import com.lsm1998.tomcat.servlet.ServletResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;

import java.io.IOException;

public class HttpServletResponse implements ServletResponse
{
    // SocketChannel的封装
    protected ChannelHandlerContext ctx;

    private ServletOutputStream outputStream;

    private HttpRequest req;

    protected String contentType;

    public HttpServletResponse(ChannelHandlerContext ctx, HttpRequest req)
    {
        this.ctx = ctx;
        this.req = req;
        this.contentType="text/html;";
        this.outputStream=new ServletOutputStream(this);
    }

    public void setContentType(String contentType)
    {
        this.contentType=contentType;
    }

    public String getContentType()
    {
        return this.contentType;
    }

    public ServletOutputStream getWrite()
    {
        return outputStream;
    }
}
