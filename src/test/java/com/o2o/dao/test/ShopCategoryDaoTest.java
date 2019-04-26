package com.o2o.dao.test;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.o2o.dao.ShopCategoryDao;
import com.o2o.entity.ShopCategory;

import junit.framework.Assert;

/**
 * 
 * @Author yukai
 * @Date 2018年7月13日
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ShopCategoryDaoTest {
    @Autowired
    private ShopCategoryDao shopCategoryDao;

    @Test
    public void testQueryShopCategory() {
        List<ShopCategory> list = shopCategoryDao.queryShopCategory(new ShopCategory());
        System.out.println(list.get(0));
        Assert.assertEquals(1, list.size());
        ShopCategory shopCategory = new ShopCategory();
        ShopCategory parentCategory = new ShopCategory();
        parentCategory.setShopCategoryId(1L);
        shopCategory.setParent(parentCategory);
        List<ShopCategory> list1 = shopCategoryDao.queryShopCategory(shopCategory);
        System.out.println(list1.get(0));
        Assert.assertEquals(1, list1.size());
    }
}
