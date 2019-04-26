package com.o2o.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.o2o.dao.ShopDao;
import com.o2o.dto.ImageHolder;
import com.o2o.dto.ShopExecution;
import com.o2o.entity.Shop;
import com.o2o.enums.ShopStateEnum;
import com.o2o.exceptions.ShopOperationException;
import com.o2o.service.ShopService;
import com.o2o.util.ImageUtil;
import com.o2o.util.PageToRowUtil;
import com.o2o.util.PathUtil;

/**
 * @Author yukai
 * @Date 2018年7月14日
 */
@Service
public class ShopServiceImpl implements ShopService {

    @Autowired
    private ShopDao shopDao;

    @Override
    @Transactional
    public ShopExecution addShop(Shop shop, ImageHolder thuminal) {
        // 判断shop是否为空
        if (shop == null) {
            return new ShopExecution(ShopStateEnum.SHOP_ENPTY);
        }
        try {
            // 初始化数据
            shop.setEnableStatus(0);
            shop.setCreateTime(new Date());
            shop.setLastEditTime(new Date());
            shop.setAdvice(ShopStateEnum.CHECK.getStateInfo());
            // 添加店铺信息
            int effectNum = shopDao.insertShop(shop);
            if (effectNum <= 0) {
                throw new ShopOperationException("店铺创建失败");
            } else {
                if (thuminal.getImage() != null) {
                    // 存储图片
                    try {
                        addShopImg(shop, thuminal);
                    } catch (Exception e) {
                        throw new ShopOperationException("添加图片失败" + e.getMessage());
                    }
                    // 更新图片地址
                    effectNum = shopDao.updateShop(shop);
                    if (effectNum <= 0) {
                        throw new ShopOperationException("更新图片地址失败");
                    }
                }
            }
        } catch (Exception e) {
            throw new ShopOperationException("店铺创建失败");
        }
        return new ShopExecution(ShopStateEnum.CHECK);
    }

    /**
     * 增加商铺图片
     * 
     * @param shop
     * @param shopImg
     */
    private void addShopImg(Shop shop, ImageHolder thuminal) {
        String dest = PathUtil.getShopImagePath(shop.getShopId());
        String shopImgAddr = ImageUtil.generateThumbnail(thuminal, dest);
        shop.setShopImg(shopImgAddr);
    }

    /**
     * 根据商铺id获取商铺信息
     * 
     * @param id
     * @return
     */
    @Override
    public Shop getByShopId(Long id) {
        return shopDao.queryShopById(id);
    }

    /**
     * 更新商铺
     * 
     * @param shop
     * @param stream
     * @param fileName
     * @return
     */
    @Override
    public ShopExecution updateShop(Shop shop, ImageHolder thuminal) {
        if (shop == null || shop.getShopId() == null) {
            return new ShopExecution(ShopStateEnum.NULL_SHOPID);
        }
        try {
            // 1.判断图片是否需要更新
            if (thuminal.getImage() != null && thuminal.getImageName() != null
                && thuminal.getImageName().length() > 0) {
                // 先删除之前该商铺上传的图片
                Shop tempShop = shopDao.queryShopById(shop.getShopId());
                if (tempShop.getShopImg() != null) {
                    ImageUtil.deleteFileOrPath(tempShop.getShopImg());
                }
                // 添加新上传的图片
                addShopImg(shop, thuminal);
            }
            // 2.更新商铺信息
            shop.setLastEditTime(new Date());
            int effectNum = shopDao.updateShop(shop);
            if (effectNum < 0) {
                return new ShopExecution(ShopStateEnum.INNER_ERROR);
            } else {
                shop = shopDao.queryShopById(shop.getShopId());
                return new ShopExecution(ShopStateEnum.SUCCESS, shop);
            }
        } catch (Exception e) {
            throw new ShopOperationException("更新商铺信息失败");
        }
    }

    /**
     * 获取商铺列表
     * 
     * @param shop
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @Override
    public ShopExecution getShopList(Shop shop, int pageIndex, int pageSize) {
        int row = PageToRowUtil.pageToRow(pageIndex, pageSize);
        List<Shop> list = shopDao.queryShopList(shop, row, pageSize);
        int count = shopDao.queryShopCount(shop);
        ShopExecution se = new ShopExecution();
        if (list != null) {
            se.setShopList(list);
            se.setCount(count);
            se.setState(ShopStateEnum.SUCCESS.getState());
        } else {
            se.setState(ShopStateEnum.INNER_ERROR.getState());
        }
        return se;
    }

}
