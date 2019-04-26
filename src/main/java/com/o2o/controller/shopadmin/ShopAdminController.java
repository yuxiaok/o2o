package com.o2o.controller.shopadmin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author yukai
 * @Date 2018年7月15日
 */
@Controller
@RequestMapping("/shopadmin")
public class ShopAdminController {
    /**
     * 商铺注册、编辑页面
     * 
     * @return
     */
    @GetMapping("/shopoperation")
    public String shopOperation() {
        return "shop/shopoperation";
    }

    /**
     * 商铺列表
     * 
     * @return
     */
    @GetMapping("/shoplist")
    public String shopList() {
        return "shop/shoplist";
    }

    /**
     * 商店管理
     * 
     * @return
     */
    @GetMapping("/shopmanagement")
    public String shopManagement() {
        return "shop/shopmanagement";
    }

    /**
     * 商品类别管理
     * 
     * @return
     */
    @GetMapping("/productcategorymanagement")
    public String productCategoryManagement() {
        return "shop/productcategorymanagement";
    }

    /**
     * 商品增加和编辑
     * 
     * @return
     */
    @GetMapping("/productoperation")
    public String productOperation() {
        return "shop/productoperation";
    }

    /**
     * 商品管理
     * 
     * @return
     */
    @GetMapping("/productmanagement")
    public String productmanagement() {
        return "shop/productmanagement";
    }
}
