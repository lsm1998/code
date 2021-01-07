package com.lsm1998.tomcat.http;

public abstract class HttpServlet
{
    public void service(HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        // 由service方法来决定，是调用doGet或者调用doPost
        if ("GET".equalsIgnoreCase(request.getMethod()))
        {
            doGet(request, response);
        } else
        {
            doPost(request, response);
        }
    }

    public abstract void doGet(HttpServletRequest request, HttpServletResponse response) throws Exception;

    public abstract void doPost(HttpServletRequest request, HttpServletResponse response) throws Exception;
}
