package com.lsm1998.echoes.registry.client;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.lsm1998.echoes.common.domain.AjaxData;
import com.lsm1998.echoes.common.utils.HttpClientUtil;
import com.lsm1998.echoes.registry.bean.RegistryNodeBean;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

@Slf4j
public class RegistryClient
{
    private final Set<String> registrySet = new CopyOnWriteArraySet<>();
    private final String ip;
    private final Integer port;
    private final String registryUrl;
    private final Gson gson = new Gson();

    public RegistryClient(String ip, Integer port)
    {
        this.ip = ip;
        this.port = port;
        this.registryUrl = String.format("http://%s:%d", this.ip, this.port);

        PongThread pongThread = new PongThread();
        pongThread.setDaemon(true);
        pongThread.start();
    }

    /**
     * 注册
     *
     * @param serviceName
     * @param port
     */
    public void registry(String serviceName, Integer port)
    {
        String key = String.format("%s-%d", serviceName, port);
        try
        {
            HttpResponse<String> response = HttpClientUtil.post(this.registryUrl + "/echoes", Map.of("serviceName", serviceName, "port", port + ""), null);
            String body = response.body();
            System.out.println(body);
            JsonObject jsonObject = JsonParser.parseString(body).getAsJsonObject();
            if (jsonObject.has("code"))
            {
                int code = jsonObject.get("code").getAsInt();
                if (code == AjaxData.ECHOES_CODE_OK)
                {
                    System.out.println("注册成功");
                    registrySet.add(key);
                    return;
                }
            }
        } catch (IOException | InterruptedException e)
        {
            e.printStackTrace();
            registrySet.remove(key);
            throw new RuntimeException("连接注册中心失败");
        }
    }

    /**
     * 心跳
     *
     * @param serviceName
     * @param port
     */
    public void pong(String serviceName, Integer port)
    {
        try
        {
            HttpResponse<String> response = HttpClientUtil.get(String.format("%s/echoes/pong?serviceName=%s&port=%d", this.registryUrl, serviceName, port), null);
            String body = response.body();
            JsonObject jsonObject = JsonParser.parseString(body).getAsJsonObject();
            if (jsonObject.has("code"))
            {
                int code = jsonObject.get("code").getAsInt();
                if (code == AjaxData.ECHOES_CODE_OK)
                {
                    log.info("pong成功");
                    return;
                }
            }
        } catch (IOException | InterruptedException e)
        {
            e.printStackTrace();
        }
        log.error("pong失败");
    }

    public RegistryNodeBean get(String serviceName)
    {
        try
        {
            HttpResponse<String> response = HttpClientUtil.get(String.format("%s/echoes/%s", this.registryUrl, serviceName), null);
            String body = response.body();
            JsonObject jsonObject = JsonParser.parseString(body).getAsJsonObject();
            if (jsonObject.has("code"))
            {
                int code = jsonObject.get("code").getAsInt();
                if (code == AjaxData.ECHOES_CODE_OK)
                {
                    return gson.fromJson(jsonObject.get("data").toString(), RegistryNodeBean.class);
                }
            }
        } catch (IOException | InterruptedException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    class PongThread extends Thread
    {
        @Override
        public void run()
        {
            while (true)
            {
                try
                {
                    Thread.sleep(5 * 1000);
                    registrySet.forEach(e ->
                    {
                        String[] arr = e.split("-");
                        pong(arr[0], Integer.parseInt(arr[1]));
                    });
                } catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }
}
