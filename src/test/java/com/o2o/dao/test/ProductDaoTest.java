package com.o2o.dao.test;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.o2o.dao.ProductDao;
import com.o2o.entity.Product;
import com.o2o.entity.ProductCategory;
import com.o2o.entity.Shop;

/**
 * @Author yukai
 * @Date 2018年9月6日
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductDaoTest {

    @Autowired
    private ProductDao dao;

    @Test
    public void testInsertProduct() {
        ProductCategory pc = new ProductCategory();
        Shop s = new Shop();
        s.setShopId(3L);
        pc.setProductCategoryId(6L);
        Product p = new Product();
        p.setProductName("柠檬芦荟");
        p.setProductDesc("芦荟很好吃");
        p.setNormalPrice("5");
        p.setPromotionPrice("4");
        p.setCreateTime(new Date());
        p.setLastEditTime(new Date());
        p.setEnableStatus(1);
        p.setProductCategory(pc);
        p.setShop(s);
        int actual = dao.insertProduct(p);
        Assert.assertEquals(1, actual);
    }

    @Test
    public void testQueryProductByProductId() {
        long productId = 3L;
        Product product = dao.queryProductByProductId(productId);
        System.out.println(product.toString());
        Assert.assertNotNull(product);
        System.out.println(product.getShop().getShopName());
    }

    @Test
    public void testUpdateProductByProduct() {
        Product product = new Product();
        product.setProductId(3L);
        Shop shop = new Shop();
        shop.setShopId(3L);
        product.setShop(shop);
        ProductCategory pc = new ProductCategory();
        pc.setProductCategoryId(6L);
        product.setProductName("泡面");
        product.setLastEditTime(new Date());
        product.setProductCategory(pc);
        int actual = dao.updateProduct(product);
        Assert.assertEquals(1, actual);
    }

    @Test
    public void testQueryProductList() {
        Product productCondition = new Product();
        // productCondition.setProductName("2");
        productCondition.setEnableStatus(1);
        Shop s = new Shop();
        s.setShopId(3L);
        productCondition.setShop(s);
        ProductCategory c = new ProductCategory();
        c.setProductCategoryId(11L);
        productCondition.setProductCategory(c);

        List<Product> queryProductList = dao.queryProductList(productCondition, 0, 3);
        assertEquals(2, queryProductList.size());
        int queryProductCount = dao.queryProductCount(productCondition);
        assertEquals(2, queryProductCount);
    }

    @Test
    public void testUpdateProductCategoryToNull() {
        int actual = dao.updateProductCategoryToNull(11L);
        Assert.assertEquals(2, actual);
    }
}
