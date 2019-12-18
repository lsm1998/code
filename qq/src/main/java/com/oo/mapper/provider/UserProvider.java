package com.oo.mapper.provider;

import java.util.Map;

/**
 * 作者：刘时明
 * 日期：2018/10/4
 * 时间：12:22
 * 说明：
 */
public class UserProvider
{
    public String selectByMap(Map<String, String> map)
    {
        String sql = "select head_img,accNumber,nickname,age,sex,flag,autoGraph from user where 1=1 ";
        for (String key : map.keySet())
        {
            String value = map.get(key);
            if (value != null)
            {
                switch (key)
                {
                    case "nikename":
                    case "accNumber":
                        sql += " and nikename like '%" + value + "'% and accNumber like '%" + value + "'% ";
                        break;
                    case "age":
                        int min = Integer.parseInt(value.split("-")[0]);
                        int max = Integer.parseInt(value.split("-")[1]);
                        sql += " and age>" + min + " and age<" + max;
                        break;
                    case "sex":
                        sql += " and sex='" + value+"'";
                        break;
                    case "flag":
                        sql += " and flag=" + Integer.parseInt(value);
                        break;
                    default:
                        break;
                }
            }
        }
        sql += " order by age";
        System.out.println("sql=" + sql);
        return sql;
    }
}
