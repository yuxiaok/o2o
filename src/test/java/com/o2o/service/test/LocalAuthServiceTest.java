package com.o2o.service.test;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.o2o.dto.LocalAuthExecution;
import com.o2o.entity.LocalAuth;
import com.o2o.entity.PersonInfo;
import com.o2o.enums.LocalAuthStateEnum;
import com.o2o.service.LocalAuthService;

/**
 * Created by yukai 2019/4/14 14:44
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LocalAuthServiceTest {
    @Autowired
    private LocalAuthService localAuthService;

    @Test
    @Ignore
    public void testABindLocalAuth() {
        // 新增一条平台账号
        LocalAuth localAuth = new LocalAuth();
        PersonInfo personInfo = new PersonInfo();
        String username = "testusername";
        String password = "testpassword";
        personInfo.setUserId(4L);
        localAuth.setPersonInfo(personInfo);
        localAuth.setUserName(username);
        localAuth.setPassword(password);
        LocalAuthExecution localAuthExecution = localAuthService.bingLocalAuth(localAuth);
        Assert.assertEquals(LocalAuthStateEnum.SUCCESS.getState(), localAuthExecution.getState());

        localAuth = localAuthService.getLocalAuthByUserId(4L);
        System.out.println(localAuth.getUserName() + "" + localAuth.getPassword());
    }

    @Test
    public void testBModifyLocalAuth() {
        // 设置账号信息
        long userId = 4L;
        String username = "testusername";
        String password = "testpassword";
        String newPassword = "testnewpassword";

        LocalAuthExecution localAuthExecution =
            localAuthService.modifyLocalAuth(userId, username, password, newPassword);
        Assert.assertEquals(LocalAuthStateEnum.SUCCESS.getState(), localAuthExecution.getState());

        LocalAuth localAuthBuUsernameAndPwd = localAuthService.getLocalAuthBuUsernameAndPwd(username, newPassword);
        System.out.println(localAuthBuUsernameAndPwd.getUserName());
    }
}
