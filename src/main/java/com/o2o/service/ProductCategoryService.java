package com.o2o.service;

import java.util.List;

import com.o2o.dto.ProductCategoryExecution;
import com.o2o.entity.ProductCategory;

/**
 * @Author yukai
 * @Date 2018年9月5日
 */
public interface ProductCategoryService {

    /**
     * 根据店铺Id获取商品分类
     * 
     * @param shopId
     * @return
     */
    List<ProductCategory> getProductCategoryByShopId(long shopId);

    /**
     * 批量插入
     * 
     * @param list
     * @return
     */
    ProductCategoryExecution batchProductCategory(List<ProductCategory> list);

    /**
     * 删除商品类别
     * 
     * @param productCategory
     * @param shopId
     * @return
     */
    ProductCategoryExecution deleteProductCategory(long productCategory, long shopId);
}
