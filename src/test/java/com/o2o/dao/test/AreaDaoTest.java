package com.o2o.dao.test;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.o2o.dao.AreaDao;
import com.o2o.entity.Area;

import junit.framework.Assert;

/**
 * 
 * @Author yukai
 * @Date 2018年7月13日
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AreaDaoTest {
    @Autowired
    private AreaDao areaDao;

    /*@Autowired
    private AreaService service;*/

    @Test
    public void testQueryArea() {
        List<Area> list = areaDao.queryArea();
        Assert.assertEquals(2, list.size());
    }

    @Test
    public void testServiceQueryArea() {
        /*List<Area> list = service.getAreaList();
        Assert.assertEquals(2, list.size());*/
    }

}
