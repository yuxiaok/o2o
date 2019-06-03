package com.o2o.controller.fronted;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.o2o.entity.HeadLine;
import com.o2o.entity.PersonInfo;
import com.o2o.entity.ShopCategory;
import com.o2o.service.HeadLineService;
import com.o2o.service.ShopCategoryService;

/**
 * Created by yukai 2019/3/16 15:34
 */
@Controller
@RequestMapping("/fronted")
public class MainPageController {

    @Autowired
    private HeadLineService headLineService;
    @Autowired
    private ShopCategoryService shopCategoryService;

    /**
     * 初始化前端展示系统的主页信息，包括获取一级店铺类别列表以及头条列表
     */
    @GetMapping("/listmainpageinfo")
    @ResponseBody
    public Map<String, Object> listMainPageInfo(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        try {
            // 获取一级店铺类别列表（即parentId为空的ShopCategory）
            List<ShopCategory> shopCategoryList = shopCategoryService.getShopCategoryList(null);
            PersonInfo user = new PersonInfo();
            user.setUserId(1L);
            user.setUserName("测试");
            request.getSession().setAttribute("user", user);
            map.put("shopCategoryList", shopCategoryList);
        } catch (Exception e) {
            map.put("success", false);
            map.put("errMsg", e.getMessage());
            return map;
        }

        try {
            // 获取状态为1的头条列表
            HeadLine headLine = new HeadLine();
            headLine.setEnableStatus(1);
            List<HeadLine> headLineList = headLineService.listHeadLine(headLine);
            map.put("headLineList", headLineList);
        } catch (Exception e) {
            map.put("success", false);
            map.put("errMsg", e.getMessage());
            return map;
        }
        map.put("success", true);
        return map;
    }
}
