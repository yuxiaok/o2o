package com.o2o.service;

import java.util.List;

import com.o2o.entity.ShopCategory;

/**
 * @Author yukai
 * @Date 2018年7月15日
 */
public interface ShopCategoryService {
String SCLISTKEY="shopcategorylist";
	List<ShopCategory> getShopCategoryList(ShopCategory shopCategoryCondition) throws Exception;
}
