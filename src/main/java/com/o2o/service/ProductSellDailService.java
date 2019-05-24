package com.o2o.service;

import java.util.Date;
import java.util.List;

import com.o2o.entity.ProductSellDaily;

public interface ProductSellDailService {

    /**
     * 每日定时对店铺销量进行统计
     */
    void dailyCalcuate();

    /**
     * 返回商品日销量的统计列表
     * 
     * @param productSellDaily
     * @param beginTime
     * @param endTime
     * @return
     */
    List<ProductSellDaily> listProductSellDaily(ProductSellDaily productSellDaily, Date beginTime, Date endTime);
}
