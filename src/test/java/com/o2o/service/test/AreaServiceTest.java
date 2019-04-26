package com.o2o.service.test;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.o2o.entity.Area;
import com.o2o.service.AreaService;
import com.o2o.service.CacheService;

/**
 * @Author yukai
 * @Date 2018年7月13日
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AreaServiceTest {
    @Autowired
    private AreaService areaService;
    @Autowired
    private CacheService cacheService;

    @Test
    public void testAreaService() {
        try {
            List<Area> areas = areaService.getAreaList();
            assertEquals("东苑", areas.get(0).getAreaName());
            cacheService.removeFromCache(areaService.AREALISTKEY);
            areas = areaService.getAreaList();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
