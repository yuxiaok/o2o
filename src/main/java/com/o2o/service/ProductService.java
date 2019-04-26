package com.o2o.service;

import java.util.List;

import com.o2o.dto.ImageHolder;
import com.o2o.dto.ProductExecution;
import com.o2o.entity.Product;

/**
 * @Author yukai
 * @Date 2018年9月6日
 */
public interface ProductService {

    /**
     * 根据查询条件获取商品列表并分页，可输入的条件有：商品名（模糊），商品状态，店铺id，商品类别
     * 
     * @param productCondition
     *            条件
     * @param pageIndex
     *            页码
     * @param pageSize
     *            页大小
     * @return
     */
    ProductExecution getProductList(Product productContidion, int pageIndex, int pageSize);

    /**
     * 添加产品
     * 
     * @param product
     *            产品
     * @param thuminal
     *            缩略图
     * @param productImgList
     *            详情图
     * @return
     */
    ProductExecution addProduct(Product product, ImageHolder thuminal, List<ImageHolder> productImgList);

    /**
     * 查找product
     * 
     * @param productId
     * @return
     */
    Product queryProductByProductId(long productId);

    /**
     * 修改商品信息
     * 
     * @param product
     *            商品
     * @param thuminal
     *            缩略图
     * @param productImgList
     *            详情图
     * @return
     */
    ProductExecution modifyProduct(Product product, ImageHolder thuminal, List<ImageHolder> productImgList);

}
