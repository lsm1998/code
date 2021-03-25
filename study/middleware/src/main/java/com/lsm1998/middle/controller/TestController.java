package com.lsm1998.middle.controller;

import com.lsm1998.middle.domain.Employee;
import com.lsm1998.middle.utils.ElasticsearchUtil;
import com.lsm1998.middle.zookeeper.WatcherApi;
import com.lsm1998.middle.zookeeper.ZkApi;
import org.apache.commons.lang3.StringUtils;
import org.apache.zookeeper.Watcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("test")
public class TestController
{
    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Autowired(required = false)
    private ZkApi zkApi;

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
        return ElasticsearchUtil.addData(employee, Employee.EMPLOYEE_INDEX, Employee.EMPLOYEE_TYPE);
    }

    @GetMapping("es/{id}")
    public Object find(@PathVariable String id)
    {
        if (StringUtils.isNotBlank(id))
        {
            return ElasticsearchUtil.searchDataById(Employee.EMPLOYEE_INDEX, Employee.EMPLOYEE_TYPE, id, null);
        } else
        {
            return "is is null!!!";
        }
    }
}
