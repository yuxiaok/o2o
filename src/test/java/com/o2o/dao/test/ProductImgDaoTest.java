package com.o2o.dao.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.o2o.dao.ProductImgDao;
import com.o2o.entity.ProductImg;

/**
 * @Author yukai
 * @Date 2018年9月6日
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductImgDaoTest {

    @Autowired
    private ProductImgDao dao;

    @Test
    public void testBatchInsertProductImg() {
        ProductImg p1 = new ProductImg();
        p1.setImgAddr("fdsf");
        p1.setPriority(20);
        p1.setCreateTime(new Date());
        p1.setProductId(2L);
        p1.setImdDesc("图片1");
        ProductImg p2 = new ProductImg();
        p2.setImgAddr("fdsf");
        p2.setPriority(10);
        p2.setCreateTime(new Date());
        p2.setProductId(2L);
        p2.setImdDesc("图片2");
        ProductImg p3 = new ProductImg();
        p3.setImgAddr("fdsf");
        p3.setPriority(50);
        p3.setCreateTime(new Date());
        p3.setProductId(2L);
        p3.setImdDesc("图片3");
        List<ProductImg> list = new ArrayList<ProductImg>();
        list.add(p1);
        list.add(p2);
        list.add(p3);
        int actual = dao.batchInsertProductImg(list);
        Assert.assertEquals(3, actual);
    }

    @Test
    public void testDeleteProductImgByProductId() {
        int actual = dao.deleteProductImgByProductId(2l);
        Assert.assertEquals(3, actual);
    }

    @Test
    public void testQueryProductImgList() {
        List<ProductImg> list = dao.queryProductImgList(3l);
        Assert.assertEquals(2, list.size());
    }
}
