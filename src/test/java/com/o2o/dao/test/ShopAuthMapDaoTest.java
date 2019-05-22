package com.o2o.dao.test;

import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.o2o.dao.ShopAuthMapDao;
import com.o2o.entity.PersonInfo;
import com.o2o.entity.Shop;
import com.o2o.entity.ShopAuthMap;

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ShopAuthMapDaoTest {

    @Autowired
    private ShopAuthMapDao shopAuthMapDao;

    @Test
    public void testAInsertShopAuthMap() {
        ShopAuthMap shopAuthMap = new ShopAuthMap();
        PersonInfo employee = new PersonInfo();
        employee.setUserId(1L);
        shopAuthMap.setEmployee(employee);
        Shop shop = new Shop();
        shop.setShopId(16L);
        shopAuthMap.setShop(shop);
        shopAuthMap.setTitle("CEO");
        shopAuthMap.setTitleFlag(1);
        shopAuthMap.setCreateTime(new Date());
        shopAuthMap.setLastEditTime(new Date());
        shopAuthMap.setEnableStatus(1);
        int effectNum = shopAuthMapDao.insertShopAuthMap(shopAuthMap);
        Assert.assertEquals(1, effectNum);

        ShopAuthMap shopAuthMap1 = new ShopAuthMap();
        PersonInfo employee1 = new PersonInfo();
        employee1.setUserId(1L);
        shopAuthMap1.setEmployee(employee1);
        Shop shop1 = new Shop();
        shop1.setShopId(3L);
        shopAuthMap1.setShop(shop1);
        shopAuthMap1.setTitle("打工仔");
        shopAuthMap1.setTitleFlag(2);
        shopAuthMap1.setCreateTime(new Date());
        shopAuthMap1.setLastEditTime(new Date());
        shopAuthMap1.setEnableStatus(0);
        int effectNum1 = shopAuthMapDao.insertShopAuthMap(shopAuthMap1);
        Assert.assertEquals(1, effectNum1);
    }

    @Test
    public void testBQueryShopAuth() {
        List<ShopAuthMap> shopAuthMapList = shopAuthMapDao.queryShopAuthMapListByShopId(16L, 0, 3);
        Assert.assertEquals(1, shopAuthMapList.size());

        ShopAuthMap shopAuthMap = shopAuthMapDao.queryShopAuthMapById(shopAuthMapList.get(0).getShopAuthId());
        Assert.assertEquals("CEO", shopAuthMap.getTitle());

        int count = shopAuthMapDao.queryShopAuthCountByShopId(16L);
        Assert.assertEquals(1, count);
    }

    @Test
    public void testCUpdateShopAuth() {
        List<ShopAuthMap> shopAuthMapList = shopAuthMapDao.queryShopAuthMapListByShopId(16L, 0, 3);
        shopAuthMapList.get(0).setTitle("CCO");
        shopAuthMapList.get(0).setTitleFlag(2);
        int effectNum = shopAuthMapDao.updateShopAuthMap(shopAuthMapList.get(0));
        Assert.assertEquals(1, effectNum);

    }

    @Test
    public void testDDeleteShopAuthMap() {
        List<ShopAuthMap> shopAuthMapList = shopAuthMapDao.queryShopAuthMapListByShopId(16L, 0, 1);
        List<ShopAuthMap> shopAuthMapList1 = shopAuthMapDao.queryShopAuthMapListByShopId(3L, 0, 1);
        int effectNum = shopAuthMapDao.deleteShopAuthMap(shopAuthMapList.get(0).getShopAuthId());
        Assert.assertEquals(1, effectNum);
        int effectNum1 = shopAuthMapDao.deleteShopAuthMap(shopAuthMapList1.get(0).getShopAuthId());
        Assert.assertEquals(1, effectNum1);
    }
}
