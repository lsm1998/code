/*
 * 作者：刘时明
 * 时间：2019/12/29-21:35
 * 作用：
 */
package com.lsm1998.chat.controller;

import com.lsm1998.chat.utils.ResultUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController
{
    @GetMapping("hello")
    public Object hello()
    {
        return ResultUtil.success("hello");
    }
}
