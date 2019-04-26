package com.o2o.service.test;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.o2o.dto.WechatAuthExecution;
import com.o2o.entity.PersonInfo;
import com.o2o.entity.WechatAuth;
import com.o2o.enums.WechatAuthStateEnum;
import com.o2o.service.WechatAuthService;

/**
 * Created by yukai 2019/4/7 15:31
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class WechatAuthServiceTest {
    @Autowired
    private WechatAuthService service;

    @Test
    public void testRegister() {
        WechatAuth wechatAuth = new WechatAuth();
        PersonInfo personInfo = new PersonInfo();
        String openId = "DFSFSD";
        personInfo.setCreateTime(new Date());
        personInfo.setUserName("测试一下");
        personInfo.setUserType(1);
        wechatAuth.setPersonInfo(personInfo);
        wechatAuth.setOpenId(openId);
        wechatAuth.setCreateTime(new Date());
        WechatAuthExecution register = service.register(wechatAuth);
        Assert.assertEquals(WechatAuthStateEnum.SUCCESS.getState(), register.getState());
        WechatAuth wechatAuthByOpenId = service.getWechatAuthByOpenId(openId);
        System.out.println(wechatAuthByOpenId);
    }
}
