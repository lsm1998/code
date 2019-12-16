package com.github.nmyphp.mytomcat.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ConfigUtil {

    private static final String PROPERTIES_PATH = "server.properties";
    public static final Properties properties;

    static {
        InputStream ins = ConfigUtil.class.getClassLoader().getResourceAsStream(PROPERTIES_PATH);
        properties = new Properties();
        try {
            properties.load(ins);
        } catch (IOException e) {
            log.warn("Load properties failed", e);
        }
    }
}
