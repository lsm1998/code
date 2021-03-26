/**
 * 作者：刘时明
 * 时间：2021/3/26
 */
package com.lsm1998.middle.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ElasticConfig
{
    @Bean
    public RestHighLevelClient getRestHighLevelClient()
    {
        return new RestHighLevelClient(
                // 如果是集群再配置多个
                RestClient.builder(new HttpHost("172.16.200.2", 9200, "http"))
        );
    }
}
