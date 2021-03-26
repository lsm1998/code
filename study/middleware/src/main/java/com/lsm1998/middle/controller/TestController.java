package com.lsm1998.middle.controller;

import com.lsm1998.middle.dao.EmployeeDao;
import com.lsm1998.middle.domain.Employee;
import com.lsm1998.middle.zookeeper.WatcherApi;
import com.lsm1998.middle.zookeeper.ZkApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("test")
public class TestController
{
    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Autowired(required = false)
    private ZkApi zkApi;

    @Autowired
    private EmployeeDao template;

    @RequestMapping("kafka/send")
    public String send(@RequestParam String msg)
    {
        // 使用kafka模板发送信息
        kafkaTemplate.send("kafka0", msg);
        return "success";
    }

    @GetMapping("node")
    public Object get(String path)
    {
        return zkApi.getData(path, new WatcherApi());
    }

    @PostMapping("node")
    public Object creat(String path, String value)
    {
        zkApi.createNode(path, value);
        return "success";
    }

    @PostMapping("es")
    public Object add(@RequestBody Employee employee)
    {
        return template.save(employee);
    }

    @GetMapping("es/{id}")
    public Object find(@PathVariable String id)
    {
        return template.findById(id);
    }
}
