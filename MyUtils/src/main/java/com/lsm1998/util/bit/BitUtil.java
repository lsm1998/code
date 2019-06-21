package com.lsm1998.util.bit;

/**
 * @作者：刘时明
 * @时间：2019/6/16-17:30
 * @作用：位运算工具类，byte数组的元素顺序采用高位在前，低位在后
 */
public class BitUtil
{
    /**
     * bytes => long
     * @param arr
     * @return
     */
    public static long byteToLong(byte[] arr)
    {
        if (arr == null || arr.length != 8)
        {
            return 0;
        }
        return (((long) arr[0] << 56) +
                ((long) (arr[1] & 0xff) << 48) +
                ((long) (arr[2] & 0xff) << 40) +
                ((long) (arr[3] & 0xff) << 32) +
                ((long) (arr[4] & 0xff) << 24) +
                ((long) (arr[5] & 0xff) << 16) +
                ((long) (arr[6] & 0xff) << 8) +
                ((long) (arr[7] & 0xff) << 0));
    }

    /**
     * long => bytes
     * @param val
     * @return
     */
    public static byte[] longToBytes(long val)
    {
        byte[] bytes = new byte[8];
        bytes[0] = (byte) (val >>> 56 & 0xff);
        bytes[1] = (byte) (val >>> 48 & 0xff);
        bytes[2] = (byte) (val >>> 40 & 0xff);
        bytes[3] = (byte) (val >>> 32 & 0xff);
        bytes[4] = (byte) (val >>> 24 & 0xff);
        bytes[5] = (byte) (val >>> 16 & 0xff);
        bytes[6] = (byte) (val >>> 8 & 0xff);
        bytes[7] = (byte) (val >>> 0 & 0xff);
        return bytes;
    }

    /**
     * bytes => int
     * @param arr
     * @return
     */
    public static int byteToInt(byte[] arr)
    {
        if (arr == null || arr.length != 4)
        {
            return 0;
        }
        return (((arr[0]) << 24) +
                ((arr[1] & 0xff) << 16) +
                ((arr[2] & 0xff) << 8) +
                ((arr[3] & 0xff) << 0));
    }

    /**
     * int => bytes
     * @param val
     * @return
     */
    public static byte[] intToBytes(int val)
    {
        byte[] bytes = new byte[4];
        bytes[0] = (byte) (val >>> 24 & 0xff);
        bytes[1] = (byte) (val >>> 16 & 0xff);
        bytes[2] = (byte) (val >>> 8 & 0xff);
        bytes[3] = (byte) (val >>> 0 & 0xff);
        return bytes;
    }

    /**
     * bytes => short
     * @param arr
     * @return
     */
    public static short byteToShort(byte[] arr)
    {
        return (short) (((short) arr[0] << 8) +
                ((short) (arr[1] & 0xff) << 0));
    }

    /**
     * short => bytes
     * @param val
     * @return
     */
    public static byte[] shortToByte(short val)
    {
        byte[] bytes = new byte[2];
        bytes[0] = (byte) (val >>> 8 & 0xff);
        bytes[1] = (byte) (val >>> 0 & 0xff);
        return bytes;
    }
}
