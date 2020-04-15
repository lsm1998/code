package com.lsm1998.echoes.config;

import lombok.extern.slf4j.Slf4j;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * @program: code
 * @description:
 * @author: lsm
 * @create: 2020-04-01 17:52
 **/
@Slf4j
public class EchoesConfigStart
{
    private static final String YML_CONFIG_NAME="echoes.yml";
    private static final String JSON_CONFIG_NAME="echoes.json";

    private EchoesConfig config;

    private Yaml yaml;

    {
        yaml = new Yaml();
    }

    public void load(InputStream inputStream)
    {

    }

    public void load()
    {
        loadByFilePath(this.getClass().getResource("/").getPath()+YML_CONFIG_NAME);
    }

    public void loadByJson()
    {
        loadByFilePath(this.getClass().getResource("/").getPath()+JSON_CONFIG_NAME);
    }

    private void loadByFilePath(String filePath)
    {
        try
        {
            FileInputStream inputStream = new FileInputStream(filePath);
            this.config= EchoesConfig.parse(yaml.loadAs(inputStream, Map.class));
            if(this.config==null)
            {
                throw new RuntimeException("配置文件解析错误");
            }
        }catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public EchoesConfig config()
    {
        return config;
    }
}
