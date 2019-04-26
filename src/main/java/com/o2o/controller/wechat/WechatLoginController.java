package com.o2o.controller.wechat;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.o2o.dto.UserAccessToken;
import com.o2o.dto.WechatAuthExecution;
import com.o2o.dto.WechatUser;
import com.o2o.entity.PersonInfo;
import com.o2o.entity.WechatAuth;
import com.o2o.enums.WechatAuthStateEnum;
import com.o2o.service.PersonInfoService;
import com.o2o.service.WechatAuthService;
import com.o2o.util.wechat.WechatUtil;

/**
 * Created by yukai
 * 2019/4/6 11:33
 * 获取关注公众号之后的微信用户信息的接口，如果在微信浏览器里访问
 * https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxfbd2b36e13b30b5e&redirect_uri=http://47.104.233.230/o2o/wechatlogin/logincheck&role_type=1&response_type=code&scope=snsapi_userinfo&state=1#wechat_redirect
 * 通过访问这个路径，访问我系统的回调接口，获取的code,然后再通过code获取access_token,最后通过access_token获取用户信息
 */
@Controller
@RequestMapping("/wechatlogin")
public class WechatLoginController {

    private static Logger logger = LoggerFactory.getLogger(WechatLoginController.class);
    private static final String FRONTED = "1";
    private static final String SHOP = "2";

    @Autowired
    private WechatAuthService wechatAuthService;
    @Autowired
    private PersonInfoService personInfoService;


    @GetMapping("/logincheck")
    public String doGet(HttpServletRequest request, HttpServletResponse response) {
        logger.debug("weixin login get...");
        //获取微信公众号传过来的code，通过code可获取access_token，进而获取用户信息
        String code = request.getParameter("code");
        //判断当前的role_type(1-前端展示系统，2-店家管理系统)
        String state = request.getParameter("state");
        logger.debug("weixin login code" + code);
        WechatUser user = null;
        String openId = null;
        WechatAuth wechatAuth = null;
        if (null != code) {
            UserAccessToken token;
            try {
                // 通过code获取access_token
                token = WechatUtil.getUserAccessToken(code);
                logger.debug("weixin login token:" + token.toString());
                // 通过token获取accessToken
                String accessToken = token.getAccessToken();
                // 通过token获取openId
                openId = token.getOpenId();
                // 通过access_token和openId获取用户昵称等信息
                user = WechatUtil.getUserInfo(accessToken, openId);
                logger.debug("weixin login user:" + user.toString());
                request.getSession().setAttribute("openId", openId);
                wechatAuth = wechatAuthService.getWechatAuthByOpenId(user.getOpenId());
            } catch (IOException e) {
                logger.error("error in getUserAccessToken or getUserInfo or findByOpenId: " + e.toString());
                e.printStackTrace();
            }
        }

        //若微信账号为空则需要注册微信账号，同时注册用户信息
        if (wechatAuth == null) {
            PersonInfo personInfo = WechatUtil.getPersonInfoFromRequest(user);
            wechatAuth = new WechatAuth();
            wechatAuth.setOpenId(openId);
            if (FRONTED.equals(state)) {
                personInfo.setUserType(1);
            } else {
                personInfo.setUserType(2);
            }
            wechatAuth.setPersonInfo(personInfo);
            WechatAuthExecution register = wechatAuthService.register(wechatAuth);
            if (register.getState() != WechatAuthStateEnum.SUCCESS.getState()) {
                return null;
            } else {
                personInfo = personInfoService.getPersonInfoById(wechatAuth.getPersonInfo().getUserId());
                request.getSession().setAttribute("user",personInfo);
            }
        }

        //若用户点击的是前端展示系统按钮，则进入前端展示系统，否则进入店家管理系统
        if (FRONTED.equals(state)) {
            return "fronted/index";
        } else {
            return "shopadmin/shoplist";
        }
    }

}
