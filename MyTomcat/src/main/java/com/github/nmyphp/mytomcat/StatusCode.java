package com.github.nmyphp.mytomcat;

public enum StatusCode {
    OK(200, "OK"),
    REDIRECT(302, "Redirect"),
    NOT_FOUND(404, "Not Found"),
    SERVER_ERROR(500, "Service Error");

    public final int code;
    public final String desc;

    StatusCode(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    @Override
    public String toString() {
        return new StringBuilder("HTTP/1.1")
            .append(" ")
            .append(code)
            .append(" ")
            .append(desc)
            .toString();
    }
}
