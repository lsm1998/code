package com.lsm1998.tomcat.http;

import com.lsm1998.tomcat.servlet.ServletRequest;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.QueryStringDecoder;

import java.util.List;
import java.util.Map;

public class HttpServletRequest implements ServletRequest
{
    private ChannelHandlerContext ctx;

    private Map<String, List<String>> parameters;

    private String url;

    private HttpRequest req;

    public HttpServletRequest(ChannelHandlerContext ctx, HttpRequest req)
    {
        this.ctx = ctx;
        this.req = req;
        QueryStringDecoder decoder = new QueryStringDecoder(req.uri());
        this.parameters=decoder.parameters();
        this.url=decoder.path();
    }

    public String getUrl()
    {
        return url;
    }

    public String getMethod()
    {
        return req.method().name();
    }

    public Map<String, List<String>> getParameters()
    {
        return parameters;
    }

    public String getParameter(String name)
    {
        Map<String, List<String>> params = getParameters();
        List<String> param = params.get(name);
        if (null == param)
        {
            return null;
        } else
        {
            return param.get(0);
        }
    }

    @Override
    public Object getAttribute(String var1) {
        return null;
    }

    @Override
    public String getCharacterEncoding() {
        return null;
    }

    @Override
    public int getContentLength() {
        return 0;
    }

    @Override
    public String getContentType() {
        return null;
    }
}
