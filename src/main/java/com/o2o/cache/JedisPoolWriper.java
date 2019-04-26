package com.o2o.cache;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Created by yukai 2019/4/13 11:53
 */
public class JedisPoolWriper {
    /** 连接池对象 */
    private JedisPool jedisPool;

    public JedisPoolWriper(final JedisPoolConfig poolConfig, final String host, final int port) {
        try {
            jedisPool = new JedisPool(poolConfig, host, port);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public JedisPool getJedisPool() {
        return jedisPool;
    }

    public void setJedisPool(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }
}
