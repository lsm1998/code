package com.lsm1998.data;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// (exclude = DataSourceAutoConfiguration.class)
@SpringBootApplication
public class DataApplication
{
    // https://www.cnblogs.com/cjsblog/p/9712457.html
    // https://blog.csdn.net/u012486840/article/details/80449384
    public static void main(String[] args)
    {
        SpringApplication.run(DataApplication.class, args);
    }
}
