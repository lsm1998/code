/*
 * 作者：刘时明
 * 时间：2020/4/12-10:16
 * 作用：
 */
package com.lsm1998.jvm.rt;

import com.lsm1998.util.bit.BitObjectUtil;

import java.util.Optional;

public class StructDemo
{
    public static void main(String[] args)
    {
        ObjectStruct struct=new ObjectStruct();

        Optional<byte[]> bytes1 = BitObjectUtil.objectToBytes(struct);
        // 100字节
        bytes1.ifPresent(e-> System.out.println(e.length));

        // 将对象还原为byte数组包括了引用内容
        struct.str="hello";
        Optional<byte[]> bytes2 = BitObjectUtil.objectToBytes(struct);
        // 107字节
        // hello => 5字节  len=> 2字节
        bytes2.ifPresent(e-> System.out.println(e.length));
    }
}
