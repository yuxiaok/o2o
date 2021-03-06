package com.o2o.dao.test;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.o2o.dao.ProductSellDailyDao;

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProductSellDailyDaoTest {

    @Autowired
    private ProductSellDailyDao productSellDailyDao;

    @Test
    public void testAInsertProductSellDaily() {
        int effectNum = productSellDailyDao.insertProductSellDaily();
        Assert.assertEquals(3, effectNum);
    }

    @Test
    public void testBInsertDefaultProductSellDaily() {
        int effectNum = productSellDailyDao.insertDefaultProductSellDaily();
        Assert.assertEquals(3, effectNum);
    }

    /* @Test
    public void testCQueryProductSellDaily() {
        ProductSellDaily productSellDaily = new ProductSellDaily();
        Shop shop = new Shop();
        shop.setShopId(16L);
        productSellDaily.setShop(shop);
        List<ProductSellDaily> productSellDailies =
            productSellDailyDao.queryProductSellDailyList(productSellDaily, null, null);
        Assert.assertEquals(2, productSellDailies.size());
    }*/
}
