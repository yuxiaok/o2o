package com.o2o.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.o2o.entity.Shop;

/**
 * @Author yukai
 * @Date 2018年7月13日
 */
public interface ShopDao {

	/**
	 * 分页查询商铺列表（店名（模糊），区域，状态，类别，店主）
	* @param shopCondition
	* @param rowIndex 从第几行开始读取数据
	* @param pageSize 返回的条数
	* @return
	 */
	List<Shop> queryShopList(@Param("shopCondition") Shop shopCondition,
                             @Param("rowIndex") int rowIndex,
                             @Param("pageSize") int pageSize);

	/**
	 * 获取分页下面的总条数
	* @param shopCondition
	* @return
	 */
	int queryShopCount(@Param("shopCondition") Shop shopCondition);

	/**
	 * 通过id获取商铺信息
	 * 
	 * @param id
	 * @return
	 */
	Shop queryShopById(Long id);

	/**
	 * 新增商铺
	 * 
	 * @param shop
	 * @return int
	 */
	int insertShop(Shop shop);

	/**
	 * 更新商铺
	 * 
	 * @param shop
	 * @return
	 */
	int updateShop(Shop shop);
}
