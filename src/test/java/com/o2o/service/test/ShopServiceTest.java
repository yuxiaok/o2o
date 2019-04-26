package com.o2o.service.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.o2o.dto.ImageHolder;
import com.o2o.dto.ShopExecution;
import com.o2o.entity.Area;
import com.o2o.entity.PersonInfo;
import com.o2o.entity.Shop;
import com.o2o.entity.ShopCategory;
import com.o2o.enums.ShopStateEnum;
import com.o2o.service.ShopService;

import junit.framework.Assert;

/**
 * @Author yukai
 * @Date 2018年7月14日
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ShopServiceTest {
    @Autowired
    private ShopService shopService;

    @Test
    public void updateShopTest() throws FileNotFoundException {
        Shop shop = new Shop();
        shop.setShopId(14L);
        File file = new File("d:/top.jpg");
        InputStream stream = new FileInputStream(file);
        ImageHolder thuminal = new ImageHolder("top.jpg", stream);
        shopService.updateShop(shop, thuminal);
    }

    @Test
    public void addShopTest() {
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
        shop.setShopName("测试的商铺3");
        shop.setShopDesc("测试真麻烦3");
        shop.setShopAddr("test");
        shop.setPhone("test3");
        shop.setCreateTime(new Date());
        shop.setEnableStatus(1);
        shop.setAdvice("审核中");
        shop.setPriority(2);
        File shopImg = new File("D:/韬睿/yk_tel.png");
        InputStream imgInputStream;
        try {
            imgInputStream = new FileInputStream(shopImg);
            ImageHolder thuminal = new ImageHolder(shopImg.getName(), imgInputStream);
            ShopExecution se = shopService.addShop(shop, thuminal);
            Assert.assertEquals(ShopStateEnum.CHECK.getState(), se.getState());
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Test
    public void getShopListTest() {
        Shop shop = new Shop();
        PersonInfo owner = new PersonInfo();
        owner.setUserId(1L);
        shop.setPersonInfo(owner);
        ShopExecution se = shopService.getShopList(shop, 3, 2);
        System.out.println(se.getShopList().size());
        System.out.println(se.getCount());

        ShopCategory category = new ShopCategory();
        category.setShopCategoryId(1L);
        shop.setShopCategory(category);

        ShopExecution se1 = shopService.getShopList(shop, 1, 1);
        System.out.println(se1.getShopList().size());
        System.out.println(se1.getCount());
        System.out.println(ShopStateEnum.getShopStateEnum(se1.getState()).getStateInfo());

    }
}
