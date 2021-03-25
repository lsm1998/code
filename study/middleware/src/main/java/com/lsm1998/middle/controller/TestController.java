package com.lsm1998.middle.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("test")
public class TestController
{
    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @RequestMapping("kafka/send")
    public String send(@RequestParam String msg)
    {
        // 使用kafka模板发送信息
        kafkaTemplate.send("kafka0", msg);
        return "success";
    }
}
