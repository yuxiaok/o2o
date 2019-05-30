package com.o2o.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.o2o.dao.PersonInfoDao;
import com.o2o.dao.ShopDao;
import com.o2o.dao.UserProductMapDao;
import com.o2o.dao.UserShopMapDao;
import com.o2o.dto.UserProductMapExecution;
import com.o2o.entity.PersonInfo;
import com.o2o.entity.Shop;
import com.o2o.entity.UserProductMap;
import com.o2o.entity.UserShopMap;
import com.o2o.enums.UserProductMapStateEnum;
import com.o2o.service.UserProductMapService;
import com.o2o.util.PageToRowUtil;

@Service
public class UserProductMapServiceImpl implements UserProductMapService {

    @Autowired
    private UserProductMapDao userProductMapDao;

    @Autowired
    private UserShopMapDao userShopMapDao;

    @Autowired
    private PersonInfoDao personInfoDao;
    @Autowired
    private ShopDao shopDao;

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

    @Override
    public UserProductMapExecution addUserProductMap(UserProductMap userProductMap) throws RuntimeException {
        if (userProductMap != null && userProductMap.getUser().getUserId() != null
            && userProductMap.getShop().getShopId() != null) {
            userProductMap.setCreateTime(new Date());
            try {
                int effectedNum = userProductMapDao.insertUserProductMap(userProductMap);
                if (effectedNum <= 0) {
                    throw new RuntimeException("添加消费记录失败");
                }
                if (userProductMap.getPoint() != null && userProductMap.getPoint() > 0) {
                    UserShopMap userShopMap = userShopMapDao.queryUserShopMap(userProductMap.getUser().getUserId(),
                        userProductMap.getShop().getShopId());
                    if (userShopMap != null && userShopMap.getUserShopId() != null) {
                        // if (userShopMap.getPoint() >= userProductMap.getPoint()) {
                        userShopMap.setPoint(userShopMap.getPoint() + userProductMap.getPoint());
                        effectedNum = userShopMapDao.updateUserShopMapPoint(userShopMap);
                        if (effectedNum <= 0) {
                            throw new RuntimeException("更新积分信息失败");
                        }
                        // }

                    } else {
                        // 在店铺没有过消费记录，添加一条积分信息
                        userShopMap = compactUserShopMap4Add(userProductMap.getUser().getUserId(),
                            userProductMap.getShop().getShopId(), userProductMap.getPoint());
                        effectedNum = userShopMapDao.insertUserShopMap(userShopMap);
                        if (effectedNum <= 0) {
                            throw new RuntimeException("积分信息创建失败");
                        }
                    }
                }
                return new UserProductMapExecution(UserProductMapStateEnum.SUCCESS, userProductMap);
            } catch (Exception e) {
                throw new RuntimeException("添加授权失败:" + e.toString());
            }
        } else {
            return new UserProductMapExecution(UserProductMapStateEnum.NULL_USERPRODUCT_INFO);
        }
    }

    private UserShopMap compactUserShopMap4Add(Long userId, Long shopId, Integer point) {
        UserShopMap userShopMap = null;
        if (userId != null && shopId != null) {
            userShopMap = new UserShopMap();
            PersonInfo personInfo1 = new PersonInfo();
            personInfo1.setUserId(userId);
            userShopMap.setUser(personInfo1);
            Shop shop1 = new Shop();
            shop1.setShopId(shopId);
            userShopMap.setShop(shop1);
            userShopMap.setCreateTime(new Date());
            userShopMap.setPoint(point);
        }
        return userShopMap;
    }
}
