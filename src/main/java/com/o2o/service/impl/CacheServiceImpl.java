package com.o2o.service.impl;

import org.springframework.stereotype.Service;

import com.o2o.service.CacheService;

/**
 * Created by yukai 2019/4/13 18:28
 */
@Service
public class CacheServiceImpl implements CacheService {
    /* @Autowired
    private JedisUtil.Keys jedisKeys;*/

    /**
     * 删除以keyPrefix为前缀的所有key-value
     * 
     * @param keyPrefix
     */
    @Override
    public void removeFromCache(String keyPrefix) {
        /*  Set<String> keySet = jedisKeys.keys(keyPrefix+"*");
        for (String key:keySet
             ) {
            jedisKeys.del(key);
        }*/

    }
}
