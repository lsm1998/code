package com.github.nmyphp.mytomcat;

import lombok.Getter;

@Getter
public class ServletMapping {

    private String servletName;
    private String urlPattern;
    private String clazz;

    public ServletMapping(String servletName, String urlPattern, String clazz) {
        this.servletName = servletName;
        this.urlPattern = urlPattern;
        this.clazz = clazz;
    }

    public boolean match(String uri) {
        if (null != uri && uri.trim().length() > 0) {
            return uri.startsWith(urlPattern);
        }
        return false;
    }
}
