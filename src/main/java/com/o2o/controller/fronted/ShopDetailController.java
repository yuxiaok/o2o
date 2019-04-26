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

import com.o2o.dto.ProductExecution;
import com.o2o.entity.Product;
import com.o2o.entity.ProductCategory;
import com.o2o.entity.Shop;
import com.o2o.service.ProductCategoryService;
import com.o2o.service.ProductService;
import com.o2o.service.ShopService;
import com.o2o.util.HttpRequestUtil;

/**
 * Created by yukai
 * 2019/3/30 10:31
 * 商铺详情
 */
@Controller
@RequestMapping("/fronted")
public class ShopDetailController {

    @Autowired
    private ShopService shopService;
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductCategoryService productCategoryService;

    /**
     * 获取店铺信息以及该店铺下面的商品类别列表
     * @param servletRequest
     * @return
     */
    @GetMapping("/listshopdetailpageinfo")
    @ResponseBody
    private Map<String, Object> listShopDetailPageInfo(HttpServletRequest servletRequest) {
        Map<String, Object> modelMap = new HashMap<>();
        //获取从前端传过来的shopId
        long shopId = HttpRequestUtil.getLong(servletRequest, "shopId");
        Shop shop = null;
        List<ProductCategory> productCategoryList = null;
        if (shopId != -1L) {
            //获取店铺Id为shopId的店铺信息
            shop = shopService.getByShopId(shopId);
            //获取店铺下面的商品类别列表
            productCategoryList =  productCategoryService.getProductCategoryByShopId(shopId);
            modelMap.put("shop",shop);
            modelMap.put("productCategoryList",productCategoryList);
            modelMap.put("success",true);
        }else{
            modelMap.put("success",false);
            modelMap.put("errMsg","empty shopId");
        }
        return modelMap;
    }

    /**
     * 依据查询条件分页列出该店铺下面的所有商品
     * @param servletRequest
     * @return
     */
    @GetMapping("/listproductsbyshop")
    @ResponseBody
    private Map<String,Object> listProductsByShop(HttpServletRequest servletRequest){
        Map<String,Object> map = new HashMap<>();
        //获取页码
        int pageIndex = HttpRequestUtil.getInt(servletRequest, "pageIndex");
        //获取一页需要显示的条数
        int pageSize = HttpRequestUtil.getInt(servletRequest, "pageSize");
        //获取店铺id
        long shopId = HttpRequestUtil.getLong(servletRequest, "shopId");
        //空值判断
        if (pageIndex > -1 && pageSize > -1 && shopId > -1){
            //尝试获取商品类别id
            long productCategoryId = HttpRequestUtil.getLong(servletRequest, "productCategoryId");
            //尝试获取模糊查找的商品名
            String productName = HttpRequestUtil.getString(servletRequest, "productName");
            //组合查询条件
            Product productCondition = compactProductCondition4Search(shopId,productCategoryId,productName);
            ProductExecution productExecution = productService.getProductList(productCondition, pageIndex, pageSize);
            map.put("productList",productExecution.getProductList());
            map.put("count",productExecution.getCount());
            map.put("success",true);
        }else{
            map.put("errMsg","empty pageSize or pageIndex or shopId");
            map.put("success",false);
        }
        return map;
    }

    /**
     * 组合查询条件，并将条件封装到ProductCondition对象里返回
     */
    private Product compactProductCondition4Search(long shopId, long productCategoryId, String productName){
        Product productCondition = new Product();
        Shop shop = new Shop();
        shop.setShopId(shopId);
        productCondition.setShop(shop);
        if (productCategoryId!=-1L){
            //查询某个商品类别下面的商品列表
            ProductCategory productCategory = new ProductCategory();
            productCategory.setProductCategoryId(productCategoryId);
            productCondition.setProductCategory(productCategory);
        }

        if (productName != null){
            //查询名字里包含productName的店铺列表
            productCondition.setProductName(productName);
        }

        //只允许选出状态为上架的商品
        productCondition.setEnableStatus(1);
        return productCondition;
    }
}
