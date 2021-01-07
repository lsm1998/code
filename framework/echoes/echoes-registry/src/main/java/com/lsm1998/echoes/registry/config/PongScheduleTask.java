package com.lsm1998.echoes.registry.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
@Slf4j
public class PongScheduleTask
{
    /**
     * 10秒执行一次，清理长时间没有心跳的连接
     * <p>
     * 超过30秒即可认为过期
     */
    @Scheduled(cron = "0/20 * * * * ?")
    public void configureTasks()
    {
        log.info("PongScheduleTask start!");
    }
}
