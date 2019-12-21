/*
 * 作者：刘时明
 * 时间：2019/12/21-14:09
 * 作用：
 */
package com.test.controller;

import com.lsm1998.spring.beans.annotation.MyAutowired;
import com.lsm1998.spring.web.annotation.MyController;
import com.lsm1998.spring.web.annotation.MyJson;
import com.lsm1998.spring.web.annotation.MyRequestMapping;
import com.lsm1998.spring.web.annotation.MyRequestParam;
import com.test.entity.User;
import com.test.service.UserService;

import java.util.HashMap;
import java.util.Map;

@MyController
@MyRequestMapping("user")
public class UserController
{
    @MyAutowired
    private UserService userService;

    @MyRequestMapping(value = "add")
    @MyJson
    public Object add(User user)
    {
        return result(userService.insertUser(user));
    }

    @MyRequestMapping(value = "list")
    @MyJson
    public Object list(@MyRequestParam("page") int page,@MyRequestParam("size") int size)
    {
        return result(userService.list(page, size));
    }

    private Map<String,Object> result(Object data)
    {
        Map<String,Object> map=new HashMap<>();
        map.put("data",data);
        return map;
    }
}
