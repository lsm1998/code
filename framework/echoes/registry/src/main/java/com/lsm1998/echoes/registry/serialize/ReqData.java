/*
 * 作者：刘时明
 * 时间：2020/3/8-17:44
 * 作用：
 */
package com.lsm1998.echoes.registry.serialize;

import lombok.Data;

import java.io.Serializable;

@Data
public class ReqData implements Serializable
{
    private int code;

    private String data;
}
