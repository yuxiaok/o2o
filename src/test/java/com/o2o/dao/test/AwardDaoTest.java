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

import com.o2o.dao.AwardDao;
import com.o2o.entity.Award;

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AwardDaoTest {

    @Autowired
    private AwardDao awardDao;

    @Test
    public void testAInsertAward() throws Exception {
        long shopId = 16L;
        Award award = new Award();
        award.setAwardName("测试一");
        award.setAwardImg("test1");
        award.setAwardDesc("描述");
        award.setPoint(5);
        award.setPriority(1);
        award.setEnableStatus(1);
        award.setCreateTime(new Date());
        award.setLastEditTime(new Date());
        award.setShopId(shopId);
        int effectNum = awardDao.insertAward(award);
        Assert.assertEquals(1, effectNum);
        Award award2 = new Award();
        award2.setAwardName("测试二");
        award2.setAwardImg("test2");
        award2.setAwardDesc("描述");
        award2.setPoint(5);
        award2.setPriority(1);
        award2.setEnableStatus(1);
        award2.setCreateTime(new Date());
        award2.setLastEditTime(new Date());
        award2.setShopId(shopId);
        int effectNum2 = awardDao.insertAward(award2);
        Assert.assertEquals(1, effectNum2);
        Award award3 = new Award();
        award3.setAwardName("三");
        award3.setAwardImg("test3");
        award3.setAwardDesc("描述");
        award3.setPoint(5);
        award3.setPriority(1);
        award3.setEnableStatus(1);
        award3.setCreateTime(new Date());
        award3.setLastEditTime(new Date());
        award3.setShopId(shopId);
        int effectNum3 = awardDao.insertAward(award3);
        Assert.assertEquals(1, effectNum3);

    }

    @Test
    public void testBQueryAwardList() {
        Award award = new Award();
        List<Award> awards = awardDao.queryAwardList(award, 0, 3);
        Assert.assertEquals(3, awards.size());
        int count = awardDao.queryAwardCount(award);
        Assert.assertEquals(3, count);
        award.setAwardName("测试");
        List<Award> awardList = awardDao.queryAwardList(award, 0, 3);
        Assert.assertEquals(2, awardList.size());
        count = awardDao.queryAwardCount(award);
        Assert.assertEquals(2, count);
    }

    @Test
    public void testCQueryAwardByAwardId() {
        Award award = new Award();
        award.setAwardName("测试一");
        List<Award> awardList = awardDao.queryAwardList(award, 0, 3);
        Assert.assertEquals(1, awardList.size());
        Award award1 = awardDao.queryAwardByAwardId(awardList.get(0).getAwardId());
        Assert.assertEquals("测试一", award1.getAwardName());
    }

    @Test
    public void testDUpdateAward() {
        Award award = new Award();
        award.setAwardName("测试一");
        List<Award> awardList = awardDao.queryAwardList(award, 0, 3);
        awardList.get(0).setAwardName("第一个测试奖品");
        int effectNum = awardDao.updateAward(awardList.get(0));
        Assert.assertEquals(1, effectNum);
        Award award1 = awardDao.queryAwardByAwardId(awardList.get(0).getAwardId());
        Assert.assertEquals("第一个测试奖品", award1.getAwardName());
    }

    @Test
    public void testEDeleteAward() {
        Award award = new Award();
        award.setAwardName("测试");
        List<Award> awardList = awardDao.queryAwardList(award, 0, 3);
        Assert.assertEquals(2, awardList.size());
        for (Award a : awardList) {
            int i = awardDao.deleteAward(a.getAwardId(), a.getShopId());
            Assert.assertEquals(1, i);
        }
    }
}
