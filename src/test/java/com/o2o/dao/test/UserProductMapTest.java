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

import com.o2o.dao.UserProductMapDao;
import com.o2o.entity.PersonInfo;
import com.o2o.entity.Product;
import com.o2o.entity.Shop;
import com.o2o.entity.UserProductMap;

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserProductMapTest {
    @Autowired
    private UserProductMapDao userProductMapDao;

    @Test
    public void testAInsertUserProductMap() {
        UserProductMap userProductMap = new UserProductMap();
        PersonInfo customer = new PersonInfo();
        customer.setUserId(1L);
        userProductMap.setUser(customer);
        userProductMap.setOperator(customer);
        Product product = new Product();
        product.setProductId(7L);
        userProductMap.setProduct(product);
        Shop shop = new Shop();
        shop.setShopId(16L);
        userProductMap.setShop(shop);
        userProductMap.setCreateTime(new Date());
        int effectNum = userProductMapDao.insertUserProductMap(userProductMap);
        Assert.assertEquals(1, effectNum);

        UserProductMap userProductMap1 = new UserProductMap();
        PersonInfo customer1 = new PersonInfo();
        customer1.setUserId(1L);
        userProductMap1.setUser(customer1);
        userProductMap1.setOperator(customer1);
        Product product1 = new Product();
        product1.setProductId(7L);
        userProductMap1.setProduct(product1);
        Shop shop1 = new Shop();
        shop1.setShopId(16L);
        userProductMap1.setShop(shop1);
        userProductMap1.setCreateTime(new Date());
        int effectNum1 = userProductMapDao.insertUserProductMap(userProductMap1);
        Assert.assertEquals(1, effectNum1);

        UserProductMap userProductMap2 = new UserProductMap();
        PersonInfo customer2 = new PersonInfo();
        customer2.setUserId(1L);
        userProductMap2.setUser(customer2);
        userProductMap2.setOperator(customer2);
        Product product2 = new Product();
        product2.setProductId(2L);
        userProductMap2.setProduct(product2);
        Shop shop2 = new Shop();
        shop2.setShopId(16L);
        userProductMap2.setShop(shop2);
        userProductMap2.setCreateTime(new Date());
        int effectNum2 = userProductMapDao.insertUserProductMap(userProductMap2);
        Assert.assertEquals(1, effectNum2);

        UserProductMap userProductMap3 = new UserProductMap();
        PersonInfo customer3 = new PersonInfo();
        customer3.setUserId(1L);
        userProductMap3.setUser(customer3);
        userProductMap3.setOperator(customer3);
        Product product3 = new Product();
        product3.setProductId(3L);
        userProductMap3.setProduct(product3);
        Shop shop3 = new Shop();
        shop3.setShopId(3L);
        userProductMap3.setShop(shop3);
        userProductMap3.setCreateTime(new Date());
        int effectNum3 = userProductMapDao.insertUserProductMap(userProductMap3);
        Assert.assertEquals(1, effectNum3);

    }

    @Test
    public void testBQueryUserProductMap() {
        UserProductMap userProductMap = new UserProductMap();
        PersonInfo customer = new PersonInfo();
        customer.setUserName("测试");
        userProductMap.setUser(customer);
        List<UserProductMap> userProductMapList = userProductMapDao.queryUserProductMapList(userProductMap, 0, 2);
        Assert.assertEquals(2, userProductMapList.size());
        int count = userProductMapDao.queryUserProductMapCount(userProductMap);
        Assert.assertEquals(4, count);

        Shop shop = new Shop();
        shop.setShopId(16L);
        userProductMap.setShop(shop);
        List<UserProductMap> userProductMaps = userProductMapDao.queryUserProductMapList(userProductMap, 0, 3);
        Assert.assertEquals(3, userProductMaps.size());
        int i = userProductMapDao.queryUserProductMapCount(userProductMap);
        Assert.assertEquals(3, i);
    }
}
