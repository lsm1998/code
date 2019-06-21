package com.lsm1998.util.http;

import com.lsm1998.util.http.enums.RequestMethod;

import java.io.IOException;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

/**
 * @作者：刘时明
 * @时间：2019/5/25-16:12
 * @作用：
 */
public class MyHttpClient
{
    // 默认超时为5秒
    private long timeOut = 5 * 1000;
    private Duration duration;

    public MyHttpClient()
    {
        this.duration = Duration.ofMillis(timeOut);
    }

    public MyHttpClient(long timeOut)
    {
        this.timeOut = timeOut;
        this.duration = Duration.ofMillis(timeOut);
    }

    /**
     * 模拟同步GET请求
     *
     * @param url
     * @param headers
     * @return
     */
    public HttpResponse<String> get(String url, Map<String, String> headers, Authenticator authenticator)
    {
        try
        {
            HttpClient.Builder cBuilder = HttpClient.newBuilder();
            if (authenticator != null)
            {
                cBuilder.authenticator(new java.net.Authenticator()
                {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication()
                    {
                        return new PasswordAuthentication(authenticator.user, authenticator.pass.toCharArray());
                    }
                });
            }
            HttpClient client = cBuilder.build();
            HttpRequest.Builder builder = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .timeout(duration);
            if (headers != null)
            {
                for (Map.Entry<String, String> entry : headers.entrySet())
                {
                    builder.header(entry.getKey(), entry.getValue());
                }
            }
            HttpRequest request = builder.build();
            return client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (InterruptedException e1)
        {
            e1.printStackTrace();
        } catch (IOException e2)
        {
            e2.printStackTrace();
        }
        return null;
    }

    public HttpResponse<String> get(String url)
    {
        return get(url, null, null);
    }

    public HttpResponse<String> get(String url, Map<String, String> headers)
    {
        return get(url, headers, null);
    }

    public class Authenticator
    {
        private String user;
        private String pass;

        private Authenticator(String user, String pass)
        {
            this.user = user;
            this.pass = pass;
        }
    }

    public Authenticator createAuthenticator(String user, String pass)
    {
        return new Authenticator(user, pass);
    }
    
    public static void main(String[] args)
    {
        MyHttpClient client = new MyHttpClient();
        Map<String, String> map = new HashMap<>();
        map.put("hello", "world");
        String result = client.get("http://127.0.0.1:8080/", map).body();
        System.out.println(result);
        MyHttpClient.Authenticator authenticator = client.createAuthenticator("user", "123");
    }

    public HttpResponse<String> request(String url, RequestMethod method, Map<String,String> params)
    {
        HttpClient client=HttpClient.newHttpClient();
        return null;
    }
}
