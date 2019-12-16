package com.github.nmyphp.mytomcat;

import com.github.nmyphp.mytomcat.exception.UnkownMethodException;

public enum HttpMethod {
    GET("GET"),
    POST("POST");

    public final String code;

    HttpMethod(String code) {
        this.code = code;
    }

    public static HttpMethod match(String code) {
        for (HttpMethod method : HttpMethod.values()) {
            if (method.code.equalsIgnoreCase(code)) {
                return method;
            }
        }
        throw new UnkownMethodException();
    }
}
