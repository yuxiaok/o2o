package com.o2o.controller.superadmin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.o2o.service.AreaService;
import com.o2o.service.CacheService;
import com.o2o.service.HeadLineService;
import com.o2o.service.ShopCategoryService;

/**
 * @Author: yukai
 * @Date: 2019-05-30 09:20
 **/
@Controller
public class CacheController {
    @Autowired
    private CacheService cacheService;
    @Autowired
    private AreaService areaService;
    @Autowired
    private HeadLineService headLineService;
    @Autowired
    private ShopCategoryService shopCategoryService;

    @GetMapping("/clearcache4area")
    public String clearCache4Area() {
        cacheService.removeFromCache(areaService.AREALISTKEY);
        return "shop/operationsuccess";
    }

    @GetMapping("/clearcache4headline")
    public String clearCache4HeadLine() {
        cacheService.removeFromCache(headLineService.HLLISTKEY);
        return "shop/operationsuccess";
    }

    @GetMapping("/clearcache4shopcategory")
    public String clearCache4ShopCategory() {
        cacheService.removeFromCache(shopCategoryService.SCLISTKEY);
        return "shop/operationsuccess";
    }
}
