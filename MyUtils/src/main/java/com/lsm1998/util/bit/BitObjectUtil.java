package com.lsm1998.util.bit;

import java.io.*;
import java.util.Optional;

/**
 * @作者：刘时明
 * @时间：2019/6/17-9:07
 * @作用：
 */
public class BitObjectUtil
{
    /**
     * 对象转换为字节数组
     *
     * @param obj
     * @param <T>
     * @return
     */
    public static <T> Optional<byte[]> objectToBytes(T obj)
    {
        byte[] bytes = null;
        try (ByteArrayOutputStream out = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(out))
        {
            oos.writeObject(obj);
            oos.flush();
            bytes = out.toByteArray();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return Optional.ofNullable(bytes);
    }

    /**
     * 字节数组转换为对象
     *
     * @param bytes
     * @param <T>
     * @return
     */
    public static <T> Optional<T> bytesToObject(byte[] bytes)
    {
        T t = null;
        try (ByteArrayInputStream in = new ByteArrayInputStream(bytes);
             ObjectInputStream ois = new ObjectInputStream(in))
        {
            t = (T) ois.readObject();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return Optional.ofNullable(t);
    }
}
