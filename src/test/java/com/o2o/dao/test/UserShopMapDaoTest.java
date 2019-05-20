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

import com.o2o.dao.UserShopMapDao;
import com.o2o.entity.PersonInfo;
import com.o2o.entity.Shop;
import com.o2o.entity.UserShopMap;

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserShopMapDaoTest {

    @Autowired
    private UserShopMapDao userShopMapDao;

    @Test
    public void testAInsertUserShopMap() {
        UserShopMap userShopMap = new UserShopMap();
        PersonInfo customer = new PersonInfo();
        customer.setUserId(1L);
        userShopMap.setUser(customer);
        Shop shop = new Shop();
        shop.setShopId(16L);
        userShopMap.setShop(shop);
        userShopMap.setCreateTime(new Date());
        userShopMap.setPoint(1);
        int effectNum = userShopMapDao.insertUserShopMap(userShopMap);
        Assert.assertEquals(1, effectNum);

        UserShopMap userShopMap2 = new UserShopMap();
        PersonInfo customer2 = new PersonInfo();
        customer2.setUserId(2L);
        userShopMap2.setUser(customer2);
        Shop shop2 = new Shop();
        shop2.setShopId(15L);
        userShopMap2.setShop(shop2);
        userShopMap2.setCreateTime(new Date());
        userShopMap2.setPoint(1);
        int effectNum2 = userShopMapDao.insertUserShopMap(userShopMap2);
        Assert.assertEquals(1, effectNum2);
    }

    @Test
    public void testBQueryUserShopMap() {
        UserShopMap userShopMap = new UserShopMap();
        List<UserShopMap> userShopMapList = userShopMapDao.queryUserShopMapList(userShopMap, 0, 2);
        Assert.assertEquals(2, userShopMapList.size());
        int i = userShopMapDao.queryUserShopMapCount(userShopMap);
        Assert.assertEquals(2, i);
        Shop shop = new Shop();
        shop.setShopId(16L);
        userShopMap.setShop(shop);
        List<UserShopMap> userShopMapList1 = userShopMapDao.queryUserShopMapList(userShopMap, 0, 2);
        Assert.assertEquals(1, userShopMapList1.size());
        int i1 = userShopMapDao.queryUserShopMapCount(userShopMap);
        Assert.assertEquals(1, i1);
        UserShopMap userShopMap1 = userShopMapDao.queryUserShopMap(1L, 16L);
        Assert.assertEquals("测试", userShopMap1.getUser().getUserName());
    }

    @Test
    public void testCUpdateUserShopMap() {
        UserShopMap userShopMap = new UserShopMap();
        UserShopMap userShopMap1 = userShopMapDao.queryUserShopMap(1L, 16L);
        userShopMap1.setPoint(2);
        int i = userShopMapDao.updateUserShopMapPoint(userShopMap1);
        Assert.assertEquals(1, i);
    }
}
