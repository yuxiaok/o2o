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

import com.o2o.dto.ShopExecution;
import com.o2o.entity.Area;
import com.o2o.entity.Shop;
import com.o2o.entity.ShopCategory;
import com.o2o.service.AreaService;
import com.o2o.service.ShopCategoryService;
import com.o2o.service.ShopService;
import com.o2o.util.HttpRequestUtil;

/**
 * Created by yukai
 * 2019/3/17 10:41
 * 获取商店列表
 */
@Controller
@RequestMapping("/fronted")
public class ShopListController {
    @Autowired
    private AreaService areaService;
    @Autowired
    private ShopCategoryService shopCategoryService;
    @Autowired
    private ShopService shopService;

    /**
     * 返回商品列表页里的ShopCategoty列表(二级或者一级)，一级区域信息列表
     *
     * @param request
     * @return
     */
    @GetMapping("/listshopspageinfo")
    @ResponseBody
    private Map<String, Object> listShopsPageInfo(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        //试着从前端获取parentId
        long parentId = HttpRequestUtil.getLong(request, "parentId");
        List<ShopCategory> shopCategoryList = null;
        if (parentId != -1) {
            //如果parentId存在，则取出该一级ShopCategory下的二级ShopCategory列表
            try {
                ShopCategory shopCategoryCondition = new ShopCategory();
                ShopCategory parent = new ShopCategory();
                parent.setShopCategoryId(parentId);
                shopCategoryCondition.setParent(parent);
                shopCategoryList = shopCategoryService.getShopCategoryList(shopCategoryCondition);
            } catch (Exception e) {
                map.put("success", false);
                map.put("errMsg", e.getMessage());
            }
        } else {
            try {
                //如果parentId不存在，则取出所有一级ShopCategory（用户在首页选择的是全部商店列表）
                shopCategoryList = shopCategoryService.getShopCategoryList(null);
            } catch (Exception e) {
                map.put("success", false);
                map.put("errMsg", e.getMessage());
            }
        }
        map.put("shopCategoryList", shopCategoryList);
        try {
            //获取区域列表信息
            List<Area> areaList = areaList = areaService.getAreaList();
            map.put("areaList", areaList);
            map.put("success", true);
            return map;
        } catch (Exception e) {
            map.put("success", false);
            map.put("errMsg", e.getMessage());
        }
        return map;
    }

    /**
     * 获取指定查询条件下的店铺列表
     *
     * @param request
     * @return
     */
    @GetMapping("/listshops")
    @ResponseBody
    private Map<String, Object> listShops(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        //获取页码
        int pageIndex = HttpRequestUtil.getInt(request, "pageIndex");
        //获取页大小
        int pageSize = HttpRequestUtil.getInt(request, "pageSize");
        //非空判断
        if (pageIndex > -1 && pageSize > -1) {
            //试着获取一级类别id
            Long parentId = HttpRequestUtil.getLong(request, "parentId");
            //试着获取二级类别id
            Long shopCategoryId = HttpRequestUtil.getLong(request, "shopCategoryId");
            //试着获取区域Id
            int areaId = HttpRequestUtil.getInt(request, "areaId");
            //试着获取模糊查询的名字
            String shopName = HttpRequestUtil.getString(request, "shopName");
            //获取组合之后的查询条件
            Shop shopCondition = compactShopCondition4Search(parentId, shopCategoryId, areaId, shopName);
            //根据查询条件和分页信息获取店铺列表，并返回总数
            ShopExecution shopExecution = shopService.getShopList(shopCondition, pageIndex, pageSize);
            map.put("shopList",shopExecution.getShopList());
            map.put("count",shopExecution.getCount());
            map.put("success",true);
        }else {
            map.put("success",false);
            map.put("errMsg","empty pageSize or pageIndex");
        }
        return map;
    }

    /**
     * 组合查询条件，并将条件封装到ShopCondition对象里返回
     * @param parentId
     * @param shopCategoryId
     * @param areaId
     * @param shopName
     * @return
     */
    private Shop compactShopCondition4Search(Long parentId, Long shopCategoryId, int areaId, String shopName) {
        Shop shopCondition = new Shop();
        if (parentId != -1L){
            //查询某个一级ShopCategory下面的所有二级ShopCategory里面的店铺列表
            ShopCategory childCategory = new ShopCategory();
            ShopCategory parentCategory = new ShopCategory();
            parentCategory.setShopCategoryId(parentId);
            childCategory.setParent(parentCategory);
            shopCondition.setShopCategory(childCategory);
        }
        if (shopCategoryId != -1L){
            //查询某个二级ShopCategory下面的店铺列表
            ShopCategory shopCategory = new ShopCategory();
            shopCategory.setShopCategoryId(shopCategoryId);
            shopCondition.setShopCategory(shopCategory);
        }

        if (areaId != -1L){
            //查询位于某个区域ID下的店铺列表
            Area area = new Area();
            area.setAreaId(areaId);
            shopCondition.setArea(area);
        }

        if (shopName != null){
            //查询名字里包含shopName的店铺列表
            shopCondition.setShopName(shopName);
        }
        //前端展示的店铺都是审核成功的店铺
        shopCondition.setEnableStatus(1);
        return shopCondition;
    }
}
