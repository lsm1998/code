package com.lsm1998.tomcat;

import com.lsm1998.tomcat.http.HttpServlet;
import com.lsm1998.tomcat.http.HttpServletRequest;
import com.lsm1998.tomcat.http.HttpServletResponse;
import com.lsm1998.tomcat.resources.StaticServlet;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.HttpRequest;

import java.util.Map;

public class TomcatHandler extends ChannelInboundHandlerAdapter
{
    private final Map<String, HttpServlet> servletMapping;

    private final StaticServlet staticServlet;

    protected TomcatHandler(Map<String, HttpServlet> servletMapping, StaticServlet staticServlet)
    {
        this.servletMapping = servletMapping;
        this.staticServlet = staticServlet;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception
    {
        if (msg instanceof HttpRequest)
        {
            HttpRequest req = (HttpRequest) msg;
            HttpServletRequest request = new HttpServletRequest(ctx, req);
            HttpServletResponse response = new HttpServletResponse(ctx, req);
            String url = request.getUrl();
            try
            {
                // 先看是否有对应的servlet处理
                if (servletMapping.containsKey(url))
                {
                    servletMapping.get(url).service(request, response);
                } else
                {
                    // 没有对应的servlet则交给静态资源处理器
                    staticServlet.service(request, response);
                }
            } catch (Exception e)
            {
                // 把service抛出的异常打印出来
                e.printStackTrace();
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception
    {
    }
}
