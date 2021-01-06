package com.lsm1998.es;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;

/**
 * @program: code
 * @description:
 * @author: lsm
 * @create: 2020-04-10 17:20
 **/
@SpringBootApplication
public class EsApp
{
    public static void main(String[] args)
    {
        // http://localhost:8080/a.txt
        SpringApplication.run(EsApp.class, args);
    }

    @PostMapping
    public void upload(HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        try (ServletInputStream inputStream = request.getInputStream())
        {
            FileOutputStream fis=new FileOutputStream("a.txt");
            byte[] b = new byte[1024];
            int len = 0;
            while ((len = inputStream.read(b)) > 0)
            {
                fis.write(b,0,len);
            }
        } catch (Exception e)
        {

        }
        response.getOutputStream().write("ok".getBytes());
    }
}
