package com.o2o.controller.fronted;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.o2o.dto.AwardExecution;
import com.o2o.entity.Award;
import com.o2o.entity.PersonInfo;
import com.o2o.entity.UserShopMap;
import com.o2o.service.AwardService;
import com.o2o.service.UserAwardMapService;
import com.o2o.service.UserShopMapService;
import com.o2o.util.HttpRequestUtil;

/**
 * @Author: yukai
 * @Date: 2019-05-29 19:25
 **/
@Controller
@RequestMapping("/fronted")
public class ShopAwardController {
    @Autowired
    private AwardService awardService;
    @Autowired
    private UserShopMapService userShopMapService;
    @Autowired
    private UserAwardMapService userAwardMapService;

    @GetMapping("/listawardsbyshop")
    @ResponseBody
    public Map<String, Object> listAwardsByShop(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        int pageIndex = HttpRequestUtil.getInt(request, "pageIndex");
        int pageSize = HttpRequestUtil.getInt(request, "pageSize");
        Long shopId = HttpRequestUtil.getLong(request, "shopId");
        if (pageIndex > -1 && pageSize > -1 && shopId > -1) {
            String awardName = HttpRequestUtil.getString(request, "awardName");
            Award awardCondition = compactAwardCondition4Search(shopId, awardName);
            AwardExecution ae = awardService.getAwardList(awardCondition, pageIndex, pageSize);
            modelMap.put("awardList", ae.getAwardList());
            modelMap.put("count", ae.getCount());
            modelMap.put("success", true);
            PersonInfo user = (PersonInfo)request.getSession().getAttribute("user");
            if (user != null && user.getUserId() != null) {
                UserShopMap userShopMap = userShopMapService.getUserShopMap(user.getUserId(), shopId);
                if (userShopMap == null) {
                    modelMap.put("totalPoint", 0);
                } else {
                    modelMap.put("totalPoint", userShopMap.getPoint());
                }
            }
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "empty pageSize or pageIndex or shopId");
        }
        return modelMap;
    }

    private Award compactAwardCondition4Search(long shopId, String awardName) {
        Award awardCondition = new Award();
        awardCondition.setShopId(shopId);
        if (awardName != null) {
            awardCondition.setAwardName(awardName);
        }
        return awardCondition;
    }
}
