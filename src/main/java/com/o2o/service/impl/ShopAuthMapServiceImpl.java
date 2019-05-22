package com.o2o.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.o2o.dao.ShopAuthMapDao;
import com.o2o.dto.ShopAuthMapExecution;
import com.o2o.entity.ShopAuthMap;
import com.o2o.enums.ShopAuthMapStateEnum;
import com.o2o.exceptions.ShopAuthMapOperationException;
import com.o2o.service.ShopAuthMapService;
import com.o2o.util.PageToRowUtil;

@Service
public class ShopAuthMapServiceImpl implements ShopAuthMapService {

    @Autowired
    private ShopAuthMapDao shopAuthMapDao;

    @Override
    public ShopAuthMapExecution listShopAuthMapByShopId(Long shopId, Integer pageIndex, Integer pageSize) {
        ShopAuthMapExecution shopAuthMapExecution = new ShopAuthMapExecution();
        if (shopId != null && pageIndex != null && pageSize != null) {
            int rowIndex = PageToRowUtil.pageToRow(pageIndex, pageSize);
            List<ShopAuthMap> shopAuthMapList = shopAuthMapDao.queryShopAuthMapListByShopId(shopId, rowIndex, pageSize);
            int count = shopAuthMapDao.queryShopAuthCountByShopId(shopId);
            shopAuthMapExecution.setShopAuthMapList(shopAuthMapList);
            shopAuthMapExecution.setCount(count);
            return shopAuthMapExecution;
        }
        return null;
    }

    @Override
    public ShopAuthMap getShopAuthMapById(Long shopAuthId) {
        return shopAuthMapDao.queryShopAuthMapById(shopAuthId);
    }

    @Transactional(rollbackFor = ShopAuthMapOperationException.class)
    @Override
    public ShopAuthMapExecution addShopAuthMap(ShopAuthMap shopAuthMap) throws ShopAuthMapOperationException {
        if (shopAuthMap != null && shopAuthMap.getShop() != null && shopAuthMap.getShop().getShopId() != null
            && shopAuthMap.getEmployee() != null && shopAuthMap.getEmployee().getUserId() != null) {
            shopAuthMap.setCreateTime(new Date());
            shopAuthMap.setLastEditTime(new Date());
            shopAuthMap.setEnableStatus(1);
            shopAuthMap.setTitleFlag(0);
            try {
                int effectNum = shopAuthMapDao.insertShopAuthMap(shopAuthMap);
                if (effectNum <= 0) {
                    throw new ShopAuthMapOperationException("添加授权失败");
                }
                return new ShopAuthMapExecution(ShopAuthMapStateEnum.SUCCESS, shopAuthMap);
            } catch (Exception e) {
                throw new ShopAuthMapOperationException("添加授权失败" + e.toString());
            }
        }
        return new ShopAuthMapExecution(ShopAuthMapStateEnum.NULL_SHOPAUTH_INFO);
    }

    @Transactional(rollbackFor = ShopAuthMapOperationException.class)
    @Override
    public ShopAuthMapExecution modifyShopAuthMap(ShopAuthMap shopAuthMap) throws ShopAuthMapOperationException {
        if (shopAuthMap != null && shopAuthMap.getShopAuthId() == null) {
            return new ShopAuthMapExecution(ShopAuthMapStateEnum.NULL_SHOPAUTH_ID);
        }
        try {
            int effectNum = shopAuthMapDao.updateShopAuthMap(shopAuthMap);
            if (effectNum <= 0) {
                return new ShopAuthMapExecution(ShopAuthMapStateEnum.INNER_ERROR);
            }
            return new ShopAuthMapExecution(ShopAuthMapStateEnum.SUCCESS, shopAuthMap);
        } catch (Exception e) {
            throw new ShopAuthMapOperationException(e.toString());
        }
    }
}
