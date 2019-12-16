package com.lsm1998.springboot.servlet;

import com.lsm1998.spring.web.BaseServlet;
import com.lsm1998.springboot.util.FilePath;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @作者：刘时明
 * @时间：19-1-10-下午3:11
 * @说明：
 */
@WebServlet(urlPatterns = {"*.html", "*.js", "*.png", "*.jpg", "*.gif", "*.jpeg", "*.json", "*.txt", "*.css"}, loadOnStartup = 1)
public class StaticServlet extends HttpServlet implements BaseServlet
{
    private static Map<String, byte[]> htmlMap = new HashMap<>();

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
            int index = url.indexOf(FilePath.STATIC_PATH);
            int len = (FilePath.STATIC_PATH).length();
            htmlMap.put(url.substring(index + len).replace("\\", "/"), bytes);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        resp.setContentType("text/html; charset=utf-8");
        String url = req.getRequestURI();
        if (htmlMap.containsKey(url))
        {
            if (url.endsWith(".html"))
            {
                resp.setContentType("text/html; charset=utf-8");
                resp.getWriter().println(new String(htmlMap.get(url)));
            } else if (url.endsWith(".json"))
            {
                resp.setContentType("text/json; charset=utf-8");
                resp.getWriter().println(new String(htmlMap.get(url)));
            } else if (url.endsWith(".js"))
            {
                resp.setContentType("text/js; charset=utf-8");
                resp.getWriter().println(new String(htmlMap.get(url)));
            } else if (url.endsWith(".css"))
            {
                resp.setContentType("text/css; charset=utf-8");
                resp.getWriter().println(new String(htmlMap.get(url)));
            } else if (url.endsWith(".jpg")||url.endsWith(".gif")||url.endsWith(".jpeg")||url.endsWith(".png"))
            {
                resp.setContentType("image/jpeg; charset=utf-8");
                BufferedOutputStream bos = new BufferedOutputStream(resp.getOutputStream());
                byte[] data = htmlMap.get(url);
                bos.write(data);
                bos.close();
            }
        } else
        {
            resp.getWriter().println("<h1>404!!!</h1>");
        }
    }

    @Override
    public void loadOnStartup()
    {
        System.out.println("开始加载静态的资源");
        File file = new File(FilePath.RESOURCES_PATH + "static");
        File[] files = file.listFiles();
        for (File f : files)
        {
            toHtmlMap(f);
        }
    }
}
