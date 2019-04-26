package com.o2o.controller.fronted;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by yukai 2019/3/16 16:15
 */
@Controller
@RequestMapping("/fronted")
public class FrontedController {
    // 前端首页
    @GetMapping("/index")
    private String index() {
        return "fronted/index";
    }

    /**
     * 商铺列表
     * 
     * @return
     */
    @GetMapping("/shoplist")
    private String shopShopList() {
        return "fronted/shoplist";
    }

    /**
     * 商品详情页
     * 
     * @return
     */
    @GetMapping("/shopdetail")
    private String shopDetail() {
        return "fronted/shopdetail";
    }

    /**
     * 商品详情页
     * 
     * @return
     */
    @GetMapping("/productdetail")
    private String productDetail() {
        return "fronted/productdetail";
    }
}
