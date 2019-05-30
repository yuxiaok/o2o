package com.o2o.controller.shopadmin;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.o2o.dto.ShopAuthMapExecution;
import com.o2o.dto.UserAccessToken;
import com.o2o.dto.WechatInfo;
import com.o2o.entity.PersonInfo;
import com.o2o.entity.Shop;
import com.o2o.entity.ShopAuthMap;
import com.o2o.entity.WechatAuth;
import com.o2o.enums.ShopAuthMapStateEnum;
import com.o2o.service.PersonInfoService;
import com.o2o.service.ShopAuthMapService;
import com.o2o.service.WechatAuthService;
import com.o2o.util.CodeUtil;
import com.o2o.util.HttpRequestUtil;
import com.o2o.util.wechat.WechatUtil;

@Controller
@RequestMapping("/shopadmin")
public class ShopAuthManagementController {

    @Autowired
    private ShopAuthMapService shopAuthMapService;
    @Autowired
    private WechatAuthService wechatAuthService;
    @Autowired
    private PersonInfoService personInfoService;

    @GetMapping("/listshopauthmapsbyshop")
    @ResponseBody
    public Map<String, Object> listShopAuthMapsByShop(HttpServletRequest request) {
        HashMap<String, Object> modelMap = new HashMap<>();
        int pageIndex = HttpRequestUtil.getInt(request, "pageIndex");
        int pageSize = HttpRequestUtil.getInt(request, "pageSize");
        Shop shop = (Shop)request.getSession().getAttribute("currentShop");
        if (pageIndex > -1 && pageSize > -1 && shop != null && shop.getShopId() != null) {
            ShopAuthMapExecution shopAuthMapExecution =
                shopAuthMapService.listShopAuthMapByShopId(shop.getShopId(), pageIndex, pageSize);
            modelMap.put("shopAuthMapList", shopAuthMapExecution.getShopAuthMapList());
            modelMap.put("count", shopAuthMapExecution.getCount());
            modelMap.put("success", true);
            return modelMap;
        }
        modelMap.put("success", false);
        modelMap.put("errMsg", "empty pageSize or pageIndex or shopId");
        return modelMap;
    }

    @GetMapping("/getshopauthmapbyid")
    @ResponseBody
    public Map<String, Object> getShopAuthMapById(@RequestParam Long shopAuthId) {
        HashMap<String, Object> modelMap = new HashMap<>();
        if (shopAuthId != null && shopAuthId > -1) {
            ShopAuthMap shopAuthMap = shopAuthMapService.getShopAuthMapById(shopAuthId);
            modelMap.put("shopAuthMap", shopAuthMap);
            modelMap.put("success", true);
            return modelMap;
        }
        modelMap.put("success", false);
        modelMap.put("errMsg", "empty shopAuthId");
        return modelMap;
    }

    @PostMapping("/modifyshopauthmap")
    @ResponseBody
    public Map<String, Object> modifyShopAuthMap(String shopAuthMapStr, HttpServletRequest request) {
        HashMap<String, Object> modelMap = new HashMap<>();
        // 判断是编辑还是授权
        boolean statusChange = HttpRequestUtil.getBoolean(request, "statusChange");
        if (!statusChange && !CodeUtil.checkVerifyCode(request)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "输入了错误的验证码");
            return modelMap;
        }
        ObjectMapper objectMapper = new ObjectMapper();
        ShopAuthMap shopAuthMap = null;
        try {
            shopAuthMap = objectMapper.readValue(shopAuthMapStr, ShopAuthMap.class);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.toString());
            return modelMap;
        }

        if (shopAuthMap != null && shopAuthMap.getShopAuthId() != null) {
            try {
                // 判断操作的对象是店家本身，店家本身不支持修改
                if (!checkPermission(shopAuthMap.getShopAuthId())) {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", "无法对店家本身权限做操作（已是店铺的最高权限）");
                    return modelMap;
                }
                ShopAuthMapExecution shopAuthMapExecution = shopAuthMapService.modifyShopAuthMap(shopAuthMap);
                if (shopAuthMapExecution.getState() == ShopAuthMapStateEnum.SUCCESS.getCode()) {
                    modelMap.put("success", true);
                } else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", shopAuthMapExecution.getStateInfo());
                }
            } catch (Exception e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.toString());
                return modelMap;
            }
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "请输入要修改的授权信息");
        }
        return modelMap;
    }

    /**
     * 被操作的对象是否能够被修改
     * 
     * @param shopAuthId
     * @return
     */
    private boolean checkPermission(Long shopAuthId) {
        ShopAuthMap shopAuthMap = shopAuthMapService.getShopAuthMapById(shopAuthId);
        if (shopAuthMap.getTitleFlag() == 0) {
            return false;
        }
        return true;
    }

    @GetMapping("/addshopauthmap")
    @ResponseBody
    public String addShopAuthMap(HttpServletRequest request, HttpServletResponse response) throws IOException {
        WechatAuth auth = getEmployeeInfo(request);
        if (auth != null) {
            PersonInfo user = personInfoService.getPersonInfoById(auth.getPersonInfo().getUserId());
            // todo
            request.getSession().setAttribute("user", user);
            String qrCodeInfo = new String(URLDecoder.decode(HttpRequestUtil.getString(request, "state"), "UTF-8"));
            ObjectMapper objectMapper = new ObjectMapper();
            WechatInfo wechatInfo = null;
            try {
                wechatInfo = objectMapper.readValue(qrCodeInfo.replace("aaa", "\""), WechatInfo.class);
            } catch (Exception e) {
                return "shop/operationfail";
            }
            if (!checkQRCodeInfo(wechatInfo)) {
                return "shop/operationfail";
            }

            // 去重
            ShopAuthMapExecution allMapList =
                shopAuthMapService.listShopAuthMapByShopId(wechatInfo.getShopId(), 1, 999);
            List<ShopAuthMap> shopAuthMapList = allMapList.getShopAuthMapList();
            for (ShopAuthMap s : shopAuthMapList) {
                if (s.getEmployee().getUserId() == user.getUserId()) {
                    return "shop/operationfail";
                }
            }
            try {
                // 添加信息
                ShopAuthMap shopAuthMap = new ShopAuthMap();
                Shop shop = new Shop();
                shop.setShopId(wechatInfo.getShopId());
                shopAuthMap.setShop(shop);
                shopAuthMap.setEmployee(user);
                shopAuthMap.setTitleFlag(1);
                shopAuthMap.setTitle("员工");
                ShopAuthMapExecution shopAuthMapExecution = shopAuthMapService.addShopAuthMap(shopAuthMap);
                if (shopAuthMapExecution.getState() == ShopAuthMapStateEnum.SUCCESS.getCode()) {
                    return "shop/operationsuccess";
                }
                return "shop/operationfail";
            } catch (Exception e) {
                return "shop/operationfail";
            }
        }
        return "shop/operationfail";
    }

    /**
     * 从回调中获取数据
     * 
     * @param request
     * @return
     */
    private WechatAuth getEmployeeInfo(HttpServletRequest request) {
        String code = request.getParameter("code");
        WechatAuth wechatAuth = null;
        try {
            if (code != null) {
                UserAccessToken userAccessToken = WechatUtil.getUserAccessToken(code);
                String openId = userAccessToken.getOpenId();
                request.getSession().setAttribute("openId", openId);
                wechatAuth = wechatAuthService.getWechatAuthByOpenId(openId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return wechatAuth;
    }

    /**
     * 二维码是否过时
     * 
     * @param wechatInfo
     * @return
     */
    private boolean checkQRCodeInfo(WechatInfo wechatInfo) {
        if (wechatInfo != null && wechatInfo.getShopId() != null && wechatInfo.getCreateTime() != null) {
            long nowTime = System.currentTimeMillis();
            if ((nowTime - wechatInfo.getCreateTime()) <= 60 * 1000) {
                return true;
            }
            return false;
        }
        return false;
    }
}
