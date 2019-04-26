package com.o2o.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.o2o.entity.ShopCategory;

/**
 * @Author yukai
 * @Date 2018年7月15日
 */
public interface ShopCategoryDao {
	List<ShopCategory> queryShopCategory(@Param("shopCategoryCondition") ShopCategory shopCategory);
}	
