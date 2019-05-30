package com.o2o.controller.superadmin;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.o2o.dto.UserShopMapExecution;
import com.o2o.entity.PersonInfo;
import com.o2o.entity.Shop;
import com.o2o.entity.UserShopMap;
import com.o2o.service.UserShopMapService;
import com.o2o.util.HttpRequestUtil;

/**
 * @Author: yukai
 * @Date: 2019-05-29 14:44
 **/
@Controller
@RequestMapping("/shopadmin")
public class UserShopManagerController {

    @Autowired
    private UserShopMapService userShopMapService;

    @GetMapping("/liseusershopmapsbyshop")
    @ResponseBody
    public Map<String, Object> listUserShopMapsByShop(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        int pageIndex = HttpRequestUtil.getInt(request, "pageIndex");
        int pageSize = HttpRequestUtil.getInt(request, "pageSize");
        Shop currentShop = (Shop)request.getSession().getAttribute("currentShop");
        if (pageIndex > -1 && pageSize > -1 && currentShop != null && currentShop.getShopId() != null) {
            UserShopMap userShopMap = new UserShopMap();
            userShopMap.setShop(currentShop);
            String userName = HttpRequestUtil.getString(request, "userName");
            if (userName != null) {
                PersonInfo personInfo = new PersonInfo();
                personInfo.setUserName(userName);
                userShopMap.setUser(personInfo);
            }
            UserShopMapExecution userShopMapExecution =
                userShopMapService.listUserShopMap(userShopMap, pageIndex, pageSize);
            map.put("userShopMapList", userShopMapExecution.getUserShopMapList());
            map.put("count", userShopMapExecution.getCount());
            map.put("success", true);
        } else {
            map.put("success", false);
            map.put("errMsg", "empty pageSize or pageIndex or shopId");
        }
        return map;
    }
}
