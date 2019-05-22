package com.o2o.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.o2o.entity.ShopAuthMap;

public interface ShopAuthMapDao {

    List<ShopAuthMap> queryShopAuthMapListByShopId(@Param("shopId") long shopId, @Param("rowIndex") int rowIndex,
        @Param("pageSize") int pageSize);

    int queryShopAuthCountByShopId(long shopId);

    int insertShopAuthMap(ShopAuthMap shopAuthMap);

    int updateShopAuthMap(ShopAuthMap shopAuthMap);

    int deleteShopAuthMap(long shopAuthId);

    ShopAuthMap queryShopAuthMapById(Long shopAuthId);
}
