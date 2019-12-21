package com.lsm1998.util.file;

import org.yaml.snakeyaml.Yaml;
import java.util.Map;

/**
 * @作者：刘时明
 * @时间：2019/6/16-18:42
 * @作用：
 */
public class YmlUtil
{
    public static <E> E loadYml(String path, Class<E> clazz)
    {
        Yaml yaml = new Yaml();
        return yaml.loadAs(YmlUtil.class.getResourceAsStream("/" + path), clazz);
    }

    public static Map<String, Object> loadYml(String path)
    {
        return loadYml(path, Map.class);
    }
}
