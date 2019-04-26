package com.o2o.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.o2o.entity.Product;

/**
 * @Author yukai
 * @Date 2018年9月6日
 */
public interface ProductDao {

    /**
     * 更新商品的类别为Null
     * 
     * @param productCategoryId
     * @return
     */
    int updateProductCategoryToNull(long productCategoryId);

    /**
     * 根据查询条件获取商品列表并分页，可输入的条件有：商品名（模糊），商品状态，店铺id，商品类别
     * 
     * @param productCondition
     * @param rowIndex
     * @param pageSize
     * @return
     */
    List<Product> queryProductList(@Param("productCondition") Product productCondition, @Param("rowIndex") int rowIndex,
                                   @Param("pageSize") int pageSize);

    /**
     * 获取该查询条件的数据总数，用于分页
     * 
     * @param productCondition
     * @return
     */
    int queryProductCount(@Param("productCondition") Product productCondition);

    /**
     * 添加商品
     * 
     * @param product
     * @return
     */
    int insertProduct(Product product);

    /**
     * 根据商品id查询商品
     * 
     * @param productId
     * @return
     */
    Product queryProductByProductId(long productId);

    /**
     * 更新商品
     * 
     * @param product
     * @return
     */
    int updateProduct(Product product);
}
