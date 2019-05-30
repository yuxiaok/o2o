package com.o2o.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.o2o.dao.UserShopMapDao;
import com.o2o.dto.UserShopMapExecution;
import com.o2o.entity.UserShopMap;
import com.o2o.service.UserShopMapService;
import com.o2o.util.PageToRowUtil;

/**
 * @Author: yukai
 * @Date: 2019-05-29 14:39
 **/
@Service
public class UserShopMapServicImpl implements UserShopMapService {

    @Autowired
    private UserShopMapDao userShopMapDao;

    @Override
    public UserShopMapExecution listUserShopMap(UserShopMap userShopMap, int pageIndex, int pageSize) {
        if (userShopMap != null && pageIndex != -1 && pageSize != -1) {
            int rowIndex = PageToRowUtil.pageToRow(pageIndex, pageSize);
            List<UserShopMap> userShopMapList = userShopMapDao.queryUserShopMapList(userShopMap, rowIndex, pageSize);
            int count = userShopMapDao.queryUserShopMapCount(userShopMap);
            UserShopMapExecution userShopMapExecution = new UserShopMapExecution();
            userShopMapExecution.setUserShopMapList(userShopMapList);
            userShopMapExecution.setCount(count);
            return userShopMapExecution;
        }
        return null;
    }

    @Override
    public UserShopMap getUserShopMap(long userId, long shopId) {

        return userShopMapDao.queryUserShopMap(userId, shopId);
    }
}
