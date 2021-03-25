package com.lsm1998.middle.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.CountDownLatch;

@Configuration
@Slf4j
public class ZookeeperConfig
{
    @Value("${zookeeper.address}")
    private String connectString;

    @Value("${zookeeper.timeout}")
    private int timeout;


    @Bean(name = "zkClient")
    public ZooKeeper zkClient()
    {
        ZooKeeper zooKeeper = null;
//        try
//        {
//            final CountDownLatch countDownLatch = new CountDownLatch(1);
//            zooKeeper = new ZooKeeper(connectString, timeout, event ->
//            {
//                if (Watcher.Event.KeeperState.SyncConnected == event.getState())
//                {
//                    // 如果收到了服务端的响应事件,连接成功
//                    countDownLatch.countDown();
//                }
//            });
//            countDownLatch.await();
//            log.info("【初始化ZooKeeper连接状态....】={}", zooKeeper.getState());
//        } catch (Exception e)
//        {
//            log.error("【初始化ZooKeeper连接异常....】={1}", e);
//        }
        return zooKeeper;
    }
}

