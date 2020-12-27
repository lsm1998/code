package com.lsm1998.redis;

import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.util.Set;

/**
 * @program: code
 * @description:
 * @author: lsm
 * @create: 2020-04-02 17:59
 **/
public class Demo01
{
    Jedis jedis = new Jedis("127.0.0.1", 6379);

    @Test
    public void test1()
    {
        jedis.auth("fuck123");


        Set<String> set = jedis.keys("www_*");
        set.forEach(e -> System.out.println(get_user(e)));
    }

    public void add_user(String userKey)
    {
        long pos = jedis.bitpos(userKey, false);
        jedis.setbit(userKey, pos, true);
    }

    public long get_user(String userKey)
    {
        return jedis.bitcount(userKey);
    }
}
