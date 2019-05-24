package com.o2o.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.o2o.dao.UserProductMapDao;
import com.o2o.dto.UserProductMapExecution;
import com.o2o.entity.UserProductMap;
import com.o2o.service.UserProductMapService;
import com.o2o.util.PageToRowUtil;

@Service
public class UserProductMapServiceImpl implements UserProductMapService {

    @Autowired
    private UserProductMapDao userProductMapDao;

    @Override
    public UserProductMapExecution listUserProductMap(UserProductMap userProductMap, Integer pageIndex,
        Integer pageSize) {
        if (userProductMap != null && pageIndex != null && pageSize != null) {
            int rowIndex = PageToRowUtil.pageToRow(pageIndex, pageSize);
            List<UserProductMap> userProductMapList =
                userProductMapDao.queryUserProductMapList(userProductMap, rowIndex, pageSize);
            int count = userProductMapDao.queryUserProductMapCount(userProductMap);
            UserProductMapExecution userProductMapExecution = new UserProductMapExecution();
            userProductMapExecution.setUserProductMapList(userProductMapList);
            userProductMapExecution.setCount(count);
            return userProductMapExecution;
        }
        return null;
    }
}
