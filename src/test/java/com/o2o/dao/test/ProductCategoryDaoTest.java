package com.o2o.dao.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.o2o.dao.ProductCategoryDao;
import com.o2o.entity.ProductCategory;

/**
 * @Author yukai
 * @Date 2018年9月5日
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryDaoTest {

    @Autowired
    private ProductCategoryDao dao;

    @Test
    public void queryProductCategoryByShopIdTest() {
        List<ProductCategory> list = dao.queryProductCategoryByShopId(3L);
        System.out.println(list.size());
    }

    @Test
    public void batchInsertProductCategoryTest() {
        List<ProductCategory> list = new ArrayList<ProductCategory>();
        ProductCategory p1 = new ProductCategory();
        p1.setProductCategoryName("商铺类别1");
        p1.setPriority(3);
        p1.setShopId(3L);
        p1.setCreateTime(new java.util.Date());
        list.add(p1);
        ProductCategory p2 = new ProductCategory();
        p2.setProductCategoryName("商铺类别2");
        p2.setPriority(4);
        p2.setShopId(3L);
        p2.setCreateTime(new java.util.Date());
        list.add(p2);
        int effectNum = dao.batchInsertProductCategory(list);
        Assert.assertEquals(2, effectNum);
    }

    @Test
    public void deleteProductCategoryTest() {
        long shopId = 3l;
        List<ProductCategory> list = dao.queryProductCategoryByShopId(3L);
        for (ProductCategory p : list) {
            if (p.getProductCategoryName().equals("店铺商铺类别1") || p.getProductCategoryName().equals("店铺商铺类别2")
                || p.getProductCategoryName().equals("店铺商铺类别3")) {
                int effectNum = dao.deleteProductCategory(p.getProductCategoryId(), shopId);
                Assert.assertEquals(1, effectNum);
            }
        }
    }
}
