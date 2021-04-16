/**
 * 作者：刘时明
 * 时间：2021/4/16
 */
package com.lsm1998.logs.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class TestController
{
    @GetMapping
    public Object test(@RequestParam String val)
    {
        log.info("日志内容：{}", val);
        return "ok";
    }
}
