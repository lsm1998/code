package com.lsm1998.redis;

import org.junit.Test;
import redis.clients.jedis.Jedis;

/**
 * @program: code
 * @description:
 * @author: lsm
 * @create: 2020-04-02 17:59
 **/
public class Demo01
{
    @Test
    public void test1()
    {
        Jedis jedis=new Jedis("47.95.239.29",6379);
        jedis.set("key","hello");
        jedis.expire("key",60);
        System.out.println(jedis.get("key"));
    }
}
