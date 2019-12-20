/*
 * 作者：刘时明
 * 时间：2019/12/18-21:49
 * 作用：
 */
package com.test.controller;

import com.lsm1998.spring.beans.annotation.MyAutowired;
import com.lsm1998.spring.web.annotation.MyController;
import com.lsm1998.spring.web.annotation.MyJson;
import com.lsm1998.spring.web.annotation.MyRequestMapping;
import com.lsm1998.spring.web.annotation.MyRequestParam;
import com.lsm1998.spring.web.enums.MyRequestMethod;
import com.test.service.HelloService;

import java.util.HashMap;
import java.util.Map;

@MyController
@MyRequestMapping("hello")
public class HelloController
{
    @MyAutowired
    private HelloService helloService;

    @MyRequestMapping(method = MyRequestMethod.GET, value = "say")
    @MyJson
    public Object say(@MyRequestParam("name") String name)
    {
        Map<String,Object> map=new HashMap<>();
        map.put("code",1);
        map.put("info","ok");
        map.put("data",helloService.hello(name));
        return map;
    }
}
