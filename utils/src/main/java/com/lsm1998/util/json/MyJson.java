package com.lsm1998.util.json;

/**
 * @作者：刘时明
 * @时间：2019/5/24-16:33
 * @作用：
 */
public class MyJson
{
    public String toJson(Object object)
    {
        return JsonObject.toJsonString(object);
    }
}
