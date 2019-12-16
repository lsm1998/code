package com.lsm1998.springboot.servlet;

import com.lsm1998.spring.web.BaseServlet;
import com.lsm1998.springboot.util.FilePath;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @作者：刘时明
 * @时间：19-1-11-上午8:48
 * @说明：
 */
@WebServlet(urlPatterns = "*.red", loadOnStartup = 5)
public class TemplatesServlet extends HttpServlet implements BaseServlet
{
    private static Map<String, byte[]> templatesMap = new HashMap<>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        String url = req.getRequestURI();
        StringBuilder sb = new StringBuilder(url);
        sb.delete(sb.length() - 3, sb.length());
        sb.append("html");
        url = sb.toString();


        if (templatesMap.containsKey(url))
        {
            boolean flag = false;
            Properties properties = new Properties();
            properties.load(new FileInputStream(FilePath.RESOURCES_PATH + "key.properties"));
            for (Object key : properties.keySet())
            {
                if (req.getAttribute(key.toString()) == null)
                {
                    break;
                }
                String value = req.getAttribute(key.toString()).toString();
                if (value != null && value.equals(properties.getProperty(key.toString())))
                {
                    flag = true;
                }
                req.setAttribute(key.toString(), null);
            }

            resp.setContentType("text/html; charset=utf-8");
            if (flag)
            {
                resp.getWriter().println(new String(templatesMap.get(url)));
            } else
            {
                resp.getWriter().println("<h1>404</h1>");
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        this.doGet(req, resp);
    }

    @Override
    public void loadOnStartup()
    {
        System.out.println("开始加载模版资源");
        File file = new File(FilePath.TEMPLATES_PATH);
        File[] files = file.listFiles();
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
            int index = url.indexOf(FilePath.TEMPLATES_PATH);
            int len = (FilePath.TEMPLATES_PATH).length();
            templatesMap.put(url.substring(index + len).replace("\\", "/"), bytes);
        }
    }
}
