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
        StringBuilder sql = new StringBuilder("select head_img,accNumber,nickname,age,sex,flag,autoGraph from user where 1=1 ");
        for (String key : map.keySet())
        {
            String value = map.get(key);
            if (value != null)
            {
                switch (key)
                {
                    case "nikename":
                    case "accNumber":
                        sql.append(" and nikename like '%").append(value).append("'% and accNumber like '%").append(value).append("'% ");
                        break;
                    case "age":
                        int min = Integer.parseInt(value.split("-")[0]);
                        int max = Integer.parseInt(value.split("-")[1]);
                        sql.append(" and age>").append(min).append(" and age<").append(max);
                        break;
                    case "sex":
                        sql.append(" and sex='").append(value).append("'");
                        break;
                    case "flag":
                        sql.append(" and flag=").append(Integer.parseInt(value));
                        break;
                    default:
                        break;
                }
            }
        }
        sql.append(" order by age");
        return sql.toString();
    }
}
