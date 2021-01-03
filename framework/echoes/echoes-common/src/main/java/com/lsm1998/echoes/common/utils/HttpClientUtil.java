package com.lsm1998.echoes.common.utils;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

public class HttpClientUtil
{
    public static final int HTTP_OK = 200;

    private static final HttpClient httpClient = HttpClient.newHttpClient();

    /**
     * 普通表单提交
     *
     * @param url
     * @param paramMap
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    public static HttpResponse<String> post(String url, Map<String, String> paramMap, Map<String, String> headers) throws IOException, InterruptedException
    {
        StringBuilder param = new StringBuilder();
        if (paramMap != null)
        {
            paramMap.forEach((k, v) ->
            {
                param.append(k);
                param.append("=");
                param.append(v);
                param.append("&");
            });
        }
        if (param.length() > 0)
        {
            param.delete(param.length() - 1, param.length());
        }
        HttpRequest.Builder builder = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString(param.toString()));
        if (headers != null)
        {
            headers.forEach(builder::header);
        }
        return httpClient.send(builder.build(), HttpResponse.BodyHandlers.ofString());
    }

    /**
     * json 提交
     *
     * @param url
     * @param json
     * @param headers
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    public static HttpResponse<String> post(String url, String json, Map<String, String> headers) throws IOException, InterruptedException
    {
        HttpRequest.Builder builder = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json));
        if (headers != null)
        {
            headers.forEach(builder::header);
        }
        return httpClient.send(builder.build(), HttpResponse.BodyHandlers.ofString());
    }

    /**
     * get请求
     *
     * @param url
     * @param headers
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    public static HttpResponse<String> get(String url, Map<String, String> headers) throws IOException, InterruptedException
    {
        HttpRequest.Builder builder = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .GET();
        if (headers != null)
        {
            headers.forEach(builder::header);
        }
        return httpClient.send(builder.build(), HttpResponse.BodyHandlers.ofString());
    }
}
