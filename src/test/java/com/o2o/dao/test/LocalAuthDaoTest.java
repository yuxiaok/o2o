package com.o2o.dao.test;

import java.util.Date;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.o2o.dao.LocalAuthDao;
import com.o2o.entity.LocalAuth;
import com.o2o.entity.PersonInfo;

/**
 * Created by yukai 2019/4/14 11:03
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LocalAuthDaoTest {
    @Autowired
    private LocalAuthDao dao;
    private static final String username = "testusername";
    private static final String password = "testpassword";

    @Test
    public void testAInsertLocalAuth() {
        // 新增一条平台账号信息
        LocalAuth localAuth = new LocalAuth();
        PersonInfo personInfo = new PersonInfo();
        personInfo.setUserId(4L);
        localAuth.setPersonInfo(personInfo);
        localAuth.setUserName(username);
        localAuth.setPassword(password);
        localAuth.setCreateTime(new Date());
        int actual = dao.insertLocalAuth(localAuth);
        Assert.assertEquals(1, actual);
    }

    @Test
    public void testBQueryLocalByUserNameAndPwd() {
        LocalAuth localAuth = dao.queryLocalByUserNameAndPwd(username, password);
        Assert.assertEquals("笨小孩", localAuth.getPersonInfo().getUserName());
    }

    @Test
    public void testCQueryLocalByUserId() {
        LocalAuth localAuth = dao.queryLocalByUserId(4L);
        Assert.assertEquals("笨小孩", localAuth.getPersonInfo().getUserName());
    }

    @Test
    public void testDUpdateLocalAuth() {
        int actual = dao.updateLocalAuth(4L, username, password, "123456", new Date());
        Assert.assertEquals(1, actual);
        LocalAuth localAuth = dao.queryLocalByUserId(4L);
        System.out.println(localAuth.getPassword());
    }
}
