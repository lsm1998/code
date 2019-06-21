package com.lsm1998.util.http.parse;

import com.lsm1998.util.file.MyFiles;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @作者：刘时明
 * @时间：2019/6/16-14:34
 * @作用：
 */
public class HttpRequestParse
{
    /**
     * @param is
     * @return
     */
    public static HttpRequest parse(InputStream is)
    {
        return parse(MyFiles.getBytes(is));
    }

    private static HttpRequest parse(byte[] bytes)
    {
        HttpRequestParse parse = new HttpRequestParse();
        HttpRequest request = parse.new HttpRequest();
        StringBuilder sb = new StringBuilder(new String(bytes));
        int index1 = sb.indexOf(" ");
        if (index1 != -1)
        {
            request.method = sb.substring(0, index1);
            sb.delete(0, index1 + 1);
        }
        int index2 = sb.indexOf(" ");
        if (index2 != -1)
        {
            request.url = sb.substring(0, index2);
            sb.delete(0, index2 + 1);
        }
        int index3 = sb.indexOf("\n");
        if (index3 != -1)
        {
            request.version = sb.substring(0, index3);
            sb.delete(0, index3+1);
        }
        int index4 = sb.indexOf("\n\n");
        if (index4 == -1)
        {
            String headStr=sb.toString();
            System.out.println("headStr="+headStr);
            String[] heads = headStr.split("\n");
            for (String str : heads)
            {
                String[] ele = str.split(":");
                try
                {
                    request.requestHead.put(ele[0], ele[1]);
                }catch (ArrayIndexOutOfBoundsException e)
                {
                    continue;
                }
            }
        }
        System.out.println("map=" + request.requestHead);
        System.out.println("请求方法=" + request.method);
        System.out.println("请求路径=" + request.url);
        System.out.println("请求版本=" + request.version);
        return request;
    }

    public class HttpRequest
    {
        public String url;
        public String method;
        public String version;
        public Map<String, String> requestHead = new HashMap<>();
        public byte[] requestBody;
    }
}
