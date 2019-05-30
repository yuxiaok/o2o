package com.o2o.controller.fronted;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.o2o.dto.UserAwardMapExecution;
import com.o2o.entity.Award;
import com.o2o.entity.PersonInfo;
import com.o2o.entity.Shop;
import com.o2o.entity.UserAwardMap;
import com.o2o.enums.UserAwardMapStateEnum;
import com.o2o.service.AwardService;
import com.o2o.service.PersonInfoService;
import com.o2o.service.UserAwardMapService;
import com.o2o.util.CodeUtil;
import com.o2o.util.HttpRequestUtil;
import com.o2o.util.ShortNetAddressUtil;

@Controller
@RequestMapping("/fronted")
public class MyAwardController {
    @Autowired
    private UserAwardMapService userAwardMapService;
    @Autowired
    private AwardService awardService;
    @Autowired
    private PersonInfoService personInfoService;

    @RequestMapping(value = "/listuserawardmapsbycustomer", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> listUserAwardMapsByCustomer(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        int pageIndex = HttpRequestUtil.getInt(request, "pageIndex");
        int pageSize = HttpRequestUtil.getInt(request, "pageSize");
        PersonInfo user = (PersonInfo)request.getSession().getAttribute("user");
        if ((pageIndex > -1) && (pageSize > -1) && (user != null) && user.getUserId() != null) {
            UserAwardMap userAwardMapCondition = new UserAwardMap();
            userAwardMapCondition.setUser(user);
            long shopId = HttpRequestUtil.getLong(request, "shopId");
            if (shopId > -1) {
                Shop shop = new Shop();
                shop.setShopId(shopId);
                userAwardMapCondition.setShop(shop);
            }
            String awardName = HttpRequestUtil.getString(request, "userName");
            if (awardName != null) {
                Award award = new Award();
                award.setAwardName(awardName);
                userAwardMapCondition.setAward(award);
            }
            UserAwardMapExecution ue = userAwardMapService.listUserAwardMap(userAwardMapCondition, pageIndex, pageSize);
            modelMap.put("userAwardMapList", ue.getUserAwardMapList());
            modelMap.put("count", ue.getCount());
            modelMap.put("success", true);
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "empty pageSize or pageIndex or userId");
        }
        return modelMap;
    }

    @RequestMapping(value = "/adduserawardmap", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> addUserAwardMap(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        PersonInfo user = (PersonInfo)request.getSession().getAttribute("user");
        Long awardId = HttpRequestUtil.getLong(request, "awardId");
        UserAwardMap userAwardMap = compactUserAwardMap4Add(user, awardId);
        if (userAwardMap != null) {
            try {
                UserAwardMapExecution se = userAwardMapService.addUserAwardMap(userAwardMap);
                if (se.getState() == UserAwardMapStateEnum.SUCCESS.getState()) {
                    modelMap.put("success", true);
                } else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", se.getStateInfo());
                }
            } catch (RuntimeException e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.toString());
                return modelMap;
            }

        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "请选择领取的奖品");
        }
        return modelMap;
    }

    @GetMapping("/getawardbyuserawardid")
    @ResponseBody
    public Map<String, Object> getAwardById(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        Long userAwardId = HttpRequestUtil.getLong(request, "userAwardId");
        if (userAwardId > -1) {
            UserAwardMap userAwardMap = userAwardMapService.getUserAwardMapById(userAwardId);
            Award award = awardService.getAwardById(userAwardMap.getAward().getAwardId());
            map.put("award", award);
            map.put("usedStatus", userAwardMap.getUsedStatus());
            map.put("userAwardMap", userAwardMap);
            map.put("success", true);
        } else {
            map.put("success", false);
            map.put("errMsg", "empty awardId");
        }
        return map;
    }

    public static String prefix;
    public static String suffix;
    public static String middle;
    public static String exchangeUrl;

    @Value("${wechat.prefix}")
    public void setPrefix(String prefix) {
        MyAwardController.prefix = prefix;
    }

    @Value("${wechat.suffix}")
    public void setSuffix(String suffix) {
        MyAwardController.suffix = suffix;
    }

    @Value("${wechat.middle}")
    public void setMiddle(String middle) {
        MyAwardController.middle = middle;
    }

    @Value("${wechat.exchange.url}")
    public void setAuthUrl(String authUrl) {
        MyAwardController.exchangeUrl = authUrl;
    }

    @RequestMapping(value = "/generateqrcode4award", method = RequestMethod.GET)
    @ResponseBody
    private void generateQRCode4Product(HttpServletRequest request, HttpServletResponse response) {
        long userAwardId = HttpRequestUtil.getLong(request, "userAwardId");
        UserAwardMap userAwardMap = userAwardMapService.getUserAwardMapById(userAwardId);
        // todo
        PersonInfo user = (PersonInfo)request.getSession().getAttribute("user");
        if (userAwardMap != null && user != null && user.getUserId() != null
            && userAwardMap.getUser().getUserId() == user.getUserId()) {
            long timpStamp = System.currentTimeMillis();
            String content = "{aaauserAwardIdaaa:" + userAwardId + ",aaacustomerIdaaa:" + user.getUserId()
                + ",aaacreateTimeaaa:" + timpStamp + "}";
            try {
                String longUrl = prefix + exchangeUrl + middle + URLEncoder.encode(content, "UTF-8") + suffix;
                String shortUrl = ShortNetAddressUtil.getShortURL(longUrl);
                BitMatrix qRcodeImg = CodeUtil.generateQRCodeStream(shortUrl, response);

                MatrixToImageWriter.writeToStream(qRcodeImg, "png", response.getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private UserAwardMap compactUserAwardMap4Add(PersonInfo user, Long awardId) {
        UserAwardMap userAwardMap = null;
        if (user != null && user.getUserId() != null && awardId != -1) {
            userAwardMap = new UserAwardMap();
            PersonInfo personInfo = personInfoService.getPersonInfoById(user.getUserId());
            Award award = awardService.getAwardById(awardId);
            PersonInfo customer = new PersonInfo();
            customer.setUserId(user.getUserId());
            customer.setUserName(personInfo.getUserName());
            userAwardMap.setUser(customer);
            Award award1 = new Award();
            award1.setAwardId(awardId);
            award1.setAwardName(award.getAwardName());
            userAwardMap.setAward(award);
            Shop shop = new Shop();
            shop.setShopId(award.getShopId());
            userAwardMap.setShop(shop);
            userAwardMap.setPoint(award.getPoint());
            userAwardMap.setCreateTime(new Date());
            userAwardMap.setUsedStatus(1);
        }
        return userAwardMap;
    }
}
