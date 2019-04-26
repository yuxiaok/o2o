package com.o2o.dao.test;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.o2o.dao.PersonInfoDao;
import com.o2o.entity.PersonInfo;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PersonInfoDaoTest {

    @Autowired
    private PersonInfoDao dao;

    @Test
    public void testInsertPersonInfo() {
        // 设置新增的用户信息
        PersonInfo personInfo = new PersonInfo();
        personInfo.setUserName("我爱你");
        personInfo.setGender("女");
        personInfo.setUserType(1);
        personInfo.setCreateTime(new Date());
        personInfo.setLastEditTime(new Date());
        int effectNum = dao.insertPersonInfo(personInfo);
        assertEquals(1, effectNum);
    }

    @Test
    public void testQueryPersonInfoById() {
        long userId = 1;
        PersonInfo personInfo = dao.queryPersonInfoById(userId);
        System.out.println(personInfo);
    }

}
