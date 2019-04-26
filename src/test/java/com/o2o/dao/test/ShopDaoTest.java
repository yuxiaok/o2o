package com.o2o.dao.test;

import java.util.Date;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.o2o.dao.ShopDao;
import com.o2o.entity.Area;
import com.o2o.entity.PersonInfo;
import com.o2o.entity.Shop;
import com.o2o.entity.ShopCategory;

import junit.framework.Assert;

/**
 * @Author yukai
 * @Date 2018年7月13日
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ShopDaoTest {
    @Autowired
    private ShopDao shopDao;

    @Test
    public void testQueryShopById() {
        Long id = 16L;
        Shop shop = shopDao.queryShopById(id);
        System.out.println(shop.getArea().getAreaName());
        System.out.println(shop.getPersonInfo().getUserName());
        System.out.println(shop.getShopCategory().getShopCategoryName());
    }

    @Test
    @Ignore
    public void insertShopTest() {
        Shop shop = new Shop();
        PersonInfo owner = new PersonInfo();
        Area area = new Area();
        ShopCategory shopCategory = new ShopCategory();
        owner.setUserId(1L);
        area.setAreaId(2);
        shopCategory.setShopCategoryId(1L);
        shop.setPersonInfo(owner);
        shop.setArea(area);
        shop.setShopCategory(shopCategory);
        shop.setShopName("测试的商铺");
        shop.setShopDesc("测试真麻烦");
        shop.setShopAddr("test");
        shop.setPhone("test");
        shop.setShopImg("test");
        shop.setCreateTime(new Date());
        shop.setEnableStatus(1);
        shop.setAdvice("审核中");
        shop.setPriority(2);
        int actual = shopDao.insertShop(shop);
        System.out.println(shop.getShopId());
        Assert.assertEquals(1, actual);
    }

    @Test
    @Ignore
    public void updateShopTest() {
        Shop shop = new Shop();
        shop.setShopId(3L);
        shop.setShopDesc("更新测试");
        shop.setShopAddr("测试");
        shop.setLastEditTime(new Date());
        int actual = shopDao.updateShop(shop);
        Assert.assertEquals(1, actual);
    }

    @Test
    public void queryShopListTest() {
        Shop shop = new Shop();
        PersonInfo owner = new PersonInfo();
        owner.setUserId(1L);
        shop.setPersonInfo(owner);
        List<Shop> list = shopDao.queryShopList(shop, 0, 2);
        System.out.println(list.size());
        int num = shopDao.queryShopCount(shop);
        System.out.println(num);

        ShopCategory category = new ShopCategory();
        category.setShopCategoryId(1L);
        shop.setShopCategory(category);
        List<Shop> list1 = shopDao.queryShopList(shop, 0, 1);
        System.out.println(list1.size());
        int num1 = shopDao.queryShopCount(shop);
        System.out.println(num1);

    }

}
