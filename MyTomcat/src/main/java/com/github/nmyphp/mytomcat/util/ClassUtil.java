package com.github.nmyphp.mytomcat.util;

public class ClassUtil {

    public static Class<?> load(String clazz) {
        try {
            return Class.forName(clazz);
        } catch (ClassNotFoundException ex) {
            throw ExceptionUtil.wrap(ex);
        }
    }

    public static <T> T instance(String str) {
        try {
            Class<?> clazz = load(str);
            return (T) clazz.newInstance();
        } catch (Exception ex) {
            throw ExceptionUtil.wrap(ex);
        }
    }
}
