/**
 * 作者：刘时明
 * 时间：2021/1/21
 */
package com.lsm1998.tomcat.resources;

import com.lsm1998.tomcat.http.HttpServlet;
import com.lsm1998.tomcat.http.HttpServletRequest;
import com.lsm1998.tomcat.http.HttpServletResponse;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * 静态资源处理
 * <p>
 * 以内置Servlet的方式实现
 */
public class StaticServlet extends HttpServlet
{
    private static final String STATIC_PATH = StaticServlet.class.getResource("/static").getFile();

    private static final Map<String, byte[]> htmlMap = new HashMap<>();

    /**
     * 加载静态资源目录下的所有文件
     */
    public void init()
    {
        File file = new File(STATIC_PATH);
        File[] files = file.listFiles();
        if (files == null)
        {
            throw new RuntimeException("找不到静态资源目录");
        }
        for (File f : files)
        {
            toHtmlMap(f);
        }
    }

    private void toHtmlMap(File file)
    {
        if (file.isDirectory())
        {
            File[] files = file.listFiles();
            for (File f : files)
            {
                toHtmlMap(f);
            }
        } else
        {
            byte[] bytes = new byte[(int) file.length()];
            try (FileInputStream fis = new FileInputStream(file))
            {
                fis.read(bytes);
            } catch (Exception e)
            {
                e.printStackTrace();
            }
            String url = file.getAbsolutePath();
            int index = url.indexOf(STATIC_PATH);
            int len = (STATIC_PATH).length();
            htmlMap.put(url.substring(index + len).replace("\\", "/"), bytes);
        }
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        doPost(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        String url = request.getUrl();
        System.out.println(url);

        response.setContentType("text/html; charset=utf-8");
        if (htmlMap.containsKey(url))
        {
            if (url.endsWith(".html"))
            {
                response.setContentType("text/html; charset=utf-8");
                response.getWrite().write(new String(htmlMap.get(url)));
            } else if (url.endsWith(".json"))
            {
                response.setContentType("text/json; charset=utf-8");
                response.getWrite().write(new String(htmlMap.get(url)));
            } else if (url.endsWith(".js"))
            {
                response.setContentType("text/js; charset=utf-8");
                response.getWrite().write(new String(htmlMap.get(url)));
            } else if (url.endsWith(".css"))
            {
                response.setContentType("text/css; charset=utf-8");
                response.getWrite().write(new String(htmlMap.get(url)));
            } else if (url.endsWith(".jpg") || url.endsWith(".gif") || url.endsWith(".jpeg") || url.endsWith(".png"))
            {
                response.setContentType("image/jpeg; charset=utf-8");
                response.getWrite().writeFile(htmlMap.get(url));
            }
        } else
        {
            response.getWrite().write("<h1>404 - Not Found</h1>");
        }
    }
}
