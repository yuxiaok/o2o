package com.o2o.dao.test;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.o2o.dao.WechatAuthDao;
import com.o2o.entity.PersonInfo;
import com.o2o.entity.WechatAuth;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WechatAuthDaoTest {

    @Autowired
    private WechatAuthDao dao;

    @Test
    public void testInsertWechatAuth() {
        // 新增一条微信账号
        WechatAuth w = new WechatAuth();
        PersonInfo p = new PersonInfo();
        p.setUserId(1L);
        w.setPersonInfo(p);
        w.setOpenId("dfyuuy");
        w.setCreateTime(new Date());
        int actual = dao.insertWechatAuth(w);
        assertEquals(1, actual);
    }

    @Test
    public void testQueryWechatInfoByOpendId() {
        WechatAuth w = dao.queryWechatInfoByOpendId("dfyuuy");
        System.out.println(w);
    }

}
