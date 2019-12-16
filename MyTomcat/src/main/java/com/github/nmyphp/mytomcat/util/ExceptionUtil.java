package com.github.nmyphp.mytomcat.util;

public class ExceptionUtil {

    public static RuntimeException wrap(Exception ex) {
        return new RuntimeException(ex);
    }
}
