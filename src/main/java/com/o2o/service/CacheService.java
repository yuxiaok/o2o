package com.o2o.service;

/**
 * Created by yukai
 * 2019/4/13 18:27
 */
public interface CacheService {
    /***
     * 依据Key的前缀删除匹配该模式下的所有key-value
     * @param keyPrefix
     */
    void removeFromCache(String keyPrefix);
}
