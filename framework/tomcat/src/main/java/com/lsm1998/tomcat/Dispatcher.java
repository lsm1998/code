package com.lsm1998.tomcat;

import com.lsm1998.tomcat.util.ClassUtil;

import java.io.IOException;

import lombok.extern.slf4j.Slf4j;

/**
 * 请求分发器
 *
 */
@Slf4j
public class Dispatcher
{
    private ServletConfig servletConfig;

    public Dispatcher()
    {
        this.servletConfig = new ServletConfig();
    }

    public void dispatch(HttpRequest httpRequest, HttpResponse httpResponse)
    {
        boolean match = false;
        try
        {
            String uri = httpRequest.getUri();
            if (servletConfig.getIgnoreUri().contains(uri) || null == uri || uri.trim().length() == 0)
            {
                return;
            }
            for (ServletMapping sm : servletConfig.getServletMappings())
            {
                if (sm.match(uri))
                {
                    String clazz = sm.getClazz();
                    HttpServlet servlet = ClassUtil.instance(clazz);
                    servlet.service(httpRequest, httpResponse);
                    match = true;
                    break;
                }
            }
            if (!match)
            {
                log.error("Not found servlet for url:{}", uri);
            }
        } catch (IOException ex)
        {
            log.error("Occur Exception when dispatch. ", ex);
            httpResponse.setStatusCode(StatusCode.SERVER_ERROR);
            try
            {
                httpResponse.write(ex.getMessage());
            } catch (IOException e)
            {
                log.error("", e);
            }
        }
    }
}
