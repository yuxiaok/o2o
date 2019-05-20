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

import com.o2o.dao.UserAwardMapDao;
import com.o2o.entity.Award;
import com.o2o.entity.PersonInfo;
import com.o2o.entity.Shop;
import com.o2o.entity.UserAwardMap;

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserAwardMapDaoTest {
    @Autowired
    private UserAwardMapDao userAwardMapDao;

    @Test
    public void testAInsertUserAwardMap() {
        UserAwardMap userAwardMap = new UserAwardMap();
        PersonInfo customer = new PersonInfo();
        customer.setUserId(1L);
        userAwardMap.setUser(customer);
        userAwardMap.setOperator(customer);
        Award award = new Award();
        award.setAwardId(10L);
        userAwardMap.setAward(award);
        Shop shop = new Shop();
        shop.setShopId(16L);
        userAwardMap.setShop(shop);
        userAwardMap.setCreateTime(new Date());
        userAwardMap.setUsedStatus(1);
        userAwardMap.setPoint(1);
        int effectNum = userAwardMapDao.insertUserAwardMap(userAwardMap);
        Assert.assertEquals(1, effectNum);

        UserAwardMap userAwardMap1 = new UserAwardMap();
        PersonInfo customer1 = new PersonInfo();
        customer1.setUserId(1L);
        userAwardMap1.setUser(customer1);
        userAwardMap1.setOperator(customer1);
        Award award1 = new Award();
        award1.setAwardId(11L);
        userAwardMap1.setAward(award1);
        Shop shop1 = new Shop();
        shop1.setShopId(16L);
        userAwardMap1.setShop(shop1);
        userAwardMap1.setCreateTime(new Date());
        userAwardMap1.setUsedStatus(0);
        userAwardMap1.setPoint(1);
        int effectNum1 = userAwardMapDao.insertUserAwardMap(userAwardMap1);
        Assert.assertEquals(1, effectNum1);
    }

    @Test
    public void testBQueryUserAwardMapList() {
        UserAwardMap userAwardMap = new UserAwardMap();
        List<UserAwardMap> userAwardMaps = userAwardMapDao.queryUserAwardMapList(userAwardMap, 0, 3);
        Assert.assertEquals(2, userAwardMaps.size());
        int count = userAwardMapDao.queryUserAwardMapCount(userAwardMap);
        Assert.assertEquals(2, count);
        PersonInfo customer = new PersonInfo();
        customer.setUserName("测试");
        userAwardMap.setUser(customer);
        List<UserAwardMap> userAwardMapList = userAwardMapDao.queryUserAwardMapList(userAwardMap, 0, 3);
        Assert.assertEquals(2, userAwardMapList.size());
        int count1 = userAwardMapDao.queryUserAwardMapCount(userAwardMap);
        Assert.assertEquals(2, count1);
        userAwardMap = userAwardMapDao.queryUserAwardMapById(userAwardMapList.get(0).getUserAwardId());
        Assert.assertEquals("四", userAwardMap.getAward().getAwardName());
    }

    @Test
    public void testCUpdateUserAwardMap() {
        UserAwardMap userAwardMap = new UserAwardMap();
        PersonInfo customer = new PersonInfo();
        customer.setUserName("测试");
        userAwardMap.setUser(customer);
        List<UserAwardMap> userAwardMapList = userAwardMapDao.queryUserAwardMapList(userAwardMap, 0, 3);
        userAwardMapList.get(0).setUsedStatus(1);
        int effectNum = userAwardMapDao.updateUserAwardMap(userAwardMapList.get(0));
        Assert.assertEquals(1, effectNum);

    }
}
