/*
 * 作者：刘时明
 * 时间：2019/12/21-16:41
 * 作用：
 */
package com.lsm1998.io.ftp;

import lombok.Data;

import java.io.Serializable;

@Data
public class FileData implements Serializable
{
    // 序号
    private int seq;
    // 目标路径
    private String dist;
    // 当前包的大小（最后一个包可能会偏小）
    private int size;
    // 每个包大小标准大小
    private int maxSize;
    // 总大小
    private long total;
    private byte[] data;
}
