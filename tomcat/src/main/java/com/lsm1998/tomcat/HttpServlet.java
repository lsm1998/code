package com.lsm1998.tomcat;

import java.io.IOException;

/**
 * HttpServlet抽象实现.
 *
 * @author nmyphp
 */
public abstract class HttpServlet
{
    public void service(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException
    {
        if (HttpMethod.GET == httpRequest.getMethod())
        {
            this.doGet(httpRequest, httpResponse);
        } else if (HttpMethod.POST == httpRequest.getMethod())
        {
            this.doPost(httpRequest, httpResponse);
        }
    }

    public abstract void doGet(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException;

    public abstract void doPost(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException;
}
