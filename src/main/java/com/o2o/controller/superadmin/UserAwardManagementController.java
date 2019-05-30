package com.o2o.controller.superadmin;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.o2o.dto.ShopAuthMapExecution;
import com.o2o.dto.UserAccessToken;
import com.o2o.dto.UserAwardMapExecution;
import com.o2o.dto.WechatInfo;
import com.o2o.entity.*;
import com.o2o.enums.UserAwardMapStateEnum;
import com.o2o.service.PersonInfoService;
import com.o2o.service.ShopAuthMapService;
import com.o2o.service.UserAwardMapService;
import com.o2o.service.WechatAuthService;
import com.o2o.util.HttpRequestUtil;
import com.o2o.util.wechat.WechatUtil;

@Controller
@RequestMapping("/shopadmin")
public class UserAwardManagementController {
    @Autowired
    private UserAwardMapService userAwardMapService;
    /*@Autowired
    private AwardService awardService;*/
    @Autowired
    private PersonInfoService personInfoService;
    @Autowired
    private ShopAuthMapService shopAuthMapService;
    @Autowired
    private WechatAuthService wechatAuthService;

    @RequestMapping(value = "/listuserawardmapsbyshop", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> listUserAwardMapsByShop(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        Shop currentShop = (Shop)request.getSession().getAttribute("currentShop");
        int pageIndex = HttpRequestUtil.getInt(request, "pageIndex");
        int pageSize = HttpRequestUtil.getInt(request, "pageSize");
        if ((pageIndex > -1) && (pageSize > -1) && (currentShop != null) && (currentShop.getShopId() != null)) {
            UserAwardMap userAwardMap = new UserAwardMap();
            userAwardMap.setShop(currentShop);
            String awardName = HttpRequestUtil.getString(request, "awardName");
            if (awardName != null) {
                Award award = new Award();
                award.setAwardName(awardName);
                userAwardMap.setAward(award);
            }
            UserAwardMapExecution ue = userAwardMapService.listUserAwardMap(userAwardMap, pageIndex, pageSize);
            modelMap.put("userAwardMapList", ue.getUserAwardMapList());
            modelMap.put("count", ue.getCount());
            modelMap.put("success", true);
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "empty pageSize or pageIndex or shopId");
        }
        return modelMap;
    }

    /**
     * 操作员扫顾客二维码
     * 
     * @param request
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/exchangeaward", method = RequestMethod.POST)
    @ResponseBody
    private String exchangeAward(HttpServletRequest request) throws IOException {
        WechatAuth auth = getOperatorInfo(request);
        if (auth != null) {
            PersonInfo operator = personInfoService.getPersonInfoById(auth.getPersonInfo().getUserId());
            request.getSession().setAttribute("user", operator);
            String qrCodeinfo = new String(URLDecoder.decode(HttpRequestUtil.getString(request, "state"), "UTF-8"));
            ObjectMapper mapper = new ObjectMapper();
            WechatInfo wechatInfo = null;
            try {
                wechatInfo = mapper.readValue(qrCodeinfo.replace("aaa", "\""), WechatInfo.class);
            } catch (Exception e) {
                return "shop/operationfail";
            }
            if (!checkQRCodeInfo(wechatInfo)) {
                return "shop/operationfail";
            }
            Long userAwardId = wechatInfo.getUserAwardId();
            Long customerId = wechatInfo.getCustomerId();
            UserAwardMap userAwardMap = compactUserAwardMap4Exchange(customerId, userAwardId);
            if (userAwardMap != null) {
                try {
                    if (!checkShopAuth(operator.getUserId(), userAwardMap)) {
                        return "shop/operationfail";
                    }
                    UserAwardMapExecution se = userAwardMapService.modifyUserAwardMap(userAwardMap);
                    if (se.getState() == UserAwardMapStateEnum.SUCCESS.getState()) {
                        return "shop/operationsuccess";
                    }
                } catch (RuntimeException e) {
                    return "shop/operationfail";
                }
            }
        }
        return "shop/operationfail";
    }

    private WechatAuth getOperatorInfo(HttpServletRequest request) {
        String code = request.getParameter("code");
        WechatAuth wechatAuth = null;
        if (null != code) {
            UserAccessToken token;
            try {
                token = WechatUtil.getUserAccessToken(code);
                String openId = token.getOpenId();
                request.getSession().setAttribute("openId", openId);
                wechatAuth = wechatAuthService.getWechatAuthByOpenId(openId);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return wechatAuth;
    }

    private boolean checkQRCodeInfo(WechatInfo wechatInfo) {
        if (wechatInfo != null && wechatInfo.getUserAwardId() != null && wechatInfo.getCustomerId() != null
            && wechatInfo.getCreateTime() != null) {
            long nowTime = System.currentTimeMillis();
            if ((nowTime - wechatInfo.getCreateTime()) <= 5000) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    private UserAwardMap compactUserAwardMap4Exchange(Long customerId, Long userAwardId) {
        UserAwardMap userAwardMap = new UserAwardMap();
        if (customerId != null && userAwardId != null) {
            PersonInfo personInfo = new PersonInfo();
            personInfo.setUserId(customerId);
            userAwardMap.setUsedStatus(0);
            userAwardMap.setUser(personInfo);
        }
        return userAwardMap;
    }

    private boolean checkShopAuth(long userId, UserAwardMap userAwardMap) {
        ShopAuthMapExecution shopAuthMapExecution =
            shopAuthMapService.listShopAuthMapByShopId(userAwardMap.getShop().getShopId(), 1, 1000);
        for (ShopAuthMap shopAuthMap : shopAuthMapExecution.getShopAuthMapList()) {
            if (shopAuthMap.getEmployee().getUserId() == userId) {
                return true;
            }
        }
        return false;
    }
}
