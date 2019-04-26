package com.o2o.config.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.o2o.cache.JedisPoolWriper;
import com.o2o.cache.JedisUtil;

import redis.clients.jedis.JedisPoolConfig;

/**
 * @author kai.yu
 * @date 2019/4/26
 **/
@Configuration
public class JedisConfiguration {
    @Value("${redis.hostname}")
    private String hostName;
    @Value("${redis.port}")
    private int port;
    @Value("${redis.pool.maxActive}")
    private int maxActive;
    @Value("${redis.pool.maxIdle}")
    private int maxIdle;
    @Value("${redis.pool.maxWait}")
    private long maxWait;
    @Value("${redis.pool.testOnBorrow}")
    private boolean testOnBorrow;

    @Autowired
    private JedisPoolConfig jedisPoolConfig;
    @Autowired
    private JedisPoolWriper jedisPoolWriper;
    @Autowired
    private JedisUtil jedisUtil;

    /**
     * redis连接池配置
     * 
     * @return
     **/

    @Bean(name = "jedisPoolConfig")
    public JedisPoolConfig createJedisPoolConfig() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(maxActive);
        jedisPoolConfig.setMaxIdle(maxIdle);
        jedisPoolConfig.setMaxWaitMillis(maxWait);
        jedisPoolConfig.setTestOnBorrow(testOnBorrow);
        return jedisPoolConfig;
    }

    /**
     * 创建redis连接池
     * 
     * @return
     **/

    @Bean(name = "jedisPoolWriper")
    public JedisPoolWriper createJedisPoolWriper() {
        JedisPoolWriper jedisPoolWriper = new JedisPoolWriper(jedisPoolConfig, hostName, port);
        return jedisPoolWriper;
    }

    /**
     * redis工具类
     * 
     * @return
     **/

    @Bean(name = "jedisUtil")
    public JedisUtil createJedisUtil() {
        JedisUtil jedisUtil = new JedisUtil();
        jedisUtil.setJedisPool(jedisPoolWriper);
        return jedisUtil;
    }

    /**
     * key
     * 
     * @return
     **/

    @Bean(name = "jedisKeys")
    public JedisUtil.Keys createKeys() {
        JedisUtil.Keys keys = jedisUtil.new Keys();
        return keys;
    }

    /**
     * value-string
     * 
     * @return
     **/

    @Bean(name = "jedisStrings")
    public JedisUtil.Strings createStrings() {
        JedisUtil.Strings strings = jedisUtil.new Strings();
        return strings;
    }
}
