package com.lsm1998.tomcat.demo;


import com.lsm1998.tomcat.http.HttpServletRequest;
import com.lsm1998.tomcat.http.HttpServletResponse;
import com.lsm1998.tomcat.http.HttpServlet;

public class SecondServlet extends HttpServlet
{
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        this.doPost(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        response.write("This is Second Serlvet");
    }
}
