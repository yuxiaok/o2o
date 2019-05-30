package com.o2o.service;

import com.o2o.dto.UserShopMapExecution;
import com.o2o.entity.UserShopMap;

/**
 * @Author: yukai
 * @Date: 2019-05-29 14:35
 **/
public interface UserShopMapService {

    UserShopMapExecution listUserShopMap(UserShopMap userShopMap, int pageIndex, int pageSize);

    UserShopMap getUserShopMap(long userId, long shopId);
}
