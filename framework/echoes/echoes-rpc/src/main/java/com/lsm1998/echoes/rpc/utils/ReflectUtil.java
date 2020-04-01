package com.lsm1998.echoes.rpc.utils;

import com.lsm1998.util.reflect.TypeUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @作者：刘时明
 * @时间：2019/6/22-10:10
 * @作用：自定义反射工具类
 */
public class ReflectUtil
{
    private final Class type;
    private Object target;

    /**
     * 创建ReflectUtil对象
     *
     * @param clazz 类型
     * @param args  构造参数
     * @return
     */
    public static ReflectUtil of(Class<?> clazz, Object... args)
    {
        return new ReflectUtil(clazz, args);
    }

    /**
     * 创建ReflectUtil对象
     *
     * @param name 类的全限定名称
     * @return
     * @throws ClassNotFoundException
     */
    public static ReflectUtil of(String name) throws ClassNotFoundException
    {
        return new ReflectUtil(Class.forName(name));
    }

    /**
     * 调用方法
     *
     * @param name 方法名
     * @param args 参数
     * @return
     */
    public Object call(String name, Object... args)
    {
        try
        {
            Method method = type.getMethod(name);
            method.setAccessible(true);
            Object result = method.invoke(target, args);
            return result;
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 设置字段值
     *
     * @param name 字段名
     * @param val  字段值
     */
    public void set(String name, Object val)
    {
        try
        {
            Field field = this.type.getDeclaredField(name);
            field.setAccessible(true);
            field.set(this.target, val);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * 获取字段值
     *
     * @param name
     * @return
     */
    public Object get(String name)
    {
        try
        {
            Field field = this.type.getDeclaredField(name);
            field.setAccessible(true);
            return field.get(this.target);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    private ReflectUtil(Class<?> type, Object... args)
    {
        this.type = type;
        try
        {
            this.target = type.getConstructor(TypeUtil.types(args)).newInstance(args);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
