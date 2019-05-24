package com.o2o.service;

import com.o2o.dto.UserProductMapExecution;
import com.o2o.entity.UserProductMap;

public interface UserProductMapService {

    UserProductMapExecution listUserProductMap(UserProductMap userProductMap, Integer pageIndex, Integer pageSize);
}
