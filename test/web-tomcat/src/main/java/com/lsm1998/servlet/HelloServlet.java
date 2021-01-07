package com.lsm1998.servlet;

import com.google.gson.Gson;
import com.lsm1998.domain.Hello;
import com.lsm1998.tomcat.annotaion.WebServlet;
import com.lsm1998.tomcat.http.HttpServlet;
import com.lsm1998.tomcat.http.HttpServletRequest;
import com.lsm1998.tomcat.http.HttpServletResponse;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @program: code
 * @description: Servlet
 * @author: lsm
 * @create: 2020-04-09 10:21
 **/
@WebServlet(url = "/hello")
public class HelloServlet extends HttpServlet
{
    private static final Map<String, Object> cacheMap = new ConcurrentHashMap<>();

    static
    {
        /**
         * 模拟查询数据
         */
        cacheMap.put("1", Hello.builder().id(1L).name("lsm").build());
        cacheMap.put("2", Hello.builder().id(2L).name("lw").build());
        cacheMap.put("3", Hello.builder().id(3L).name("most").build());
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        doPost(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        response.setContentType("application/json");
        Object result = cacheMap.get(request.getParameter("id"));
        Gson gson = new Gson();
        response.getWrite().write(gson.toJson(Objects.requireNonNullElse(result, "记录找不到！")));
    }
}
