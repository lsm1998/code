/*
 * 作者：刘时明
 * 时间：2019/12/18-21:49
 * 作用：
 */
package com.test.controller;

import com.lsm1998.spring.web.annotation.MyController;
import com.lsm1998.spring.web.annotation.MyJson;
import com.lsm1998.spring.web.annotation.MyRequestMapping;
import com.lsm1998.spring.web.annotation.MyRequestParam;
import com.lsm1998.spring.web.enums.MyRequestMethod;

@MyController
@MyRequestMapping("hello")
public class HelloController
{
    @MyRequestMapping(method = MyRequestMethod.GET, value = "say")
    @MyJson
    public Object say(@MyRequestParam("name") String name)
    {
        return "hello:" + name;
    }
}
