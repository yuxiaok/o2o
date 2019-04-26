package com.o2o.service;

import com.o2o.dto.ImageHolder;
import com.o2o.dto.ShopExecution;
import com.o2o.entity.Shop;

/**
 * @Author yukai
 * @Date 2018年7月14日
 */
public interface ShopService {
	/**
	 * 注册商铺
	* @param shop
	* @param shopImgInputStream
	* @param imgName
	* @return
	 */
	ShopExecution addShop(Shop shop, ImageHolder thuminal);
	/**
	 * 根据商铺id获取商铺
	* @param id
	* @return
	 */
	Shop getByShopId(Long id);

	/**
	 * 更新商铺信息
	* @param shop
	* @param stream
	* @param fileName
	* @return
	 */
	ShopExecution updateShop(Shop shop, ImageHolder thuminal);

	/**
	 * 获取分页列表
	* @param shop
	* @param pageIndex
	* @param pageSize
	* @return
	 */
	ShopExecution getShopList(Shop shop, int pageIndex, int pageSize);
}
