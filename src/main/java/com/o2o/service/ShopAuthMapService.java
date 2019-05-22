package com.o2o.service;

import com.o2o.dto.ShopAuthMapExecution;
import com.o2o.entity.ShopAuthMap;
import com.o2o.exceptions.ShopAuthMapOperationException;

public interface ShopAuthMapService {

    ShopAuthMapExecution listShopAuthMapByShopId(Long shopId, Integer pageIndex, Integer pageSize);

    ShopAuthMap getShopAuthMapById(Long shopAuthId);

    ShopAuthMapExecution addShopAuthMap(ShopAuthMap shopAuthMap) throws ShopAuthMapOperationException;

    ShopAuthMapExecution modifyShopAuthMap(ShopAuthMap shopAuthMap) throws ShopAuthMapOperationException;
}
