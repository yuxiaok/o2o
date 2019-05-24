package com.o2o.service.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.o2o.dao.ProductSellDailyDao;
import com.o2o.entity.ProductSellDaily;
import com.o2o.service.ProductSellDailService;

@Service
public class ProductSellDailServiceImpl implements ProductSellDailService {

    private static final Logger log = LoggerFactory.getLogger(ProductSellDailServiceImpl.class);

    @Autowired
    private ProductSellDailyDao productSellDailyDao;

    @Override
    public void dailyCalcuate() {
        log.info("Quartz running");
        // 统计当天销售的商品的日销量
        productSellDailyDao.insertProductSellDaily();
        // 统计当天没有销售过的商品的日销量，日销量全部置为0，这两者互补，有执行顺序 为了使前端统计表完整
        productSellDailyDao.insertDefaultProductSellDaily();
        // System.out.println("quartz跑起来了");
    }

    @Override
    public List<ProductSellDaily> listProductSellDaily(ProductSellDaily productSellDaily, Date beginTime,
        Date endTime) {
        return productSellDailyDao.queryProductSellDailyList(productSellDaily, beginTime, endTime);
    }
}
