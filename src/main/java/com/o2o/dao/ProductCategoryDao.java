package com.o2o.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.o2o.entity.ProductCategory;

/**
 * @Author yukai
 * @Date 2018年9月5日
 */
public interface ProductCategoryDao {

    /**
     * 根据店铺id获取店铺的商品类别
     * 
     * @param shopId
     * @return
     */
    public List<ProductCategory> queryProductCategoryByShopId(long shopId);

    /**
     * 批量插入商品类别
     * 
     * @param list
     * @return
     */
    public int batchInsertProductCategory(List<ProductCategory> list);

    /**
     * 删除商品类别
     * 
     * @param productCategoryId
     * @param shopId
     * @return
     */
    public int deleteProductCategory(@Param("productCategoryId") long productCategoryId, @Param("shopId") long shopId);
}
