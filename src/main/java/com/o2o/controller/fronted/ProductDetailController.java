package com.o2o.controller.fronted;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.o2o.entity.Product;
import com.o2o.enums.ProductStateEnum;
import com.o2o.service.ProductService;
import com.o2o.util.HttpRequestUtil;

/**
 * @author kai.yu
 * @date 2019/4/24
 **/
@Controller
@RequestMapping("/fronted")
public class ProductDetailController {

    @Autowired
    private ProductService productService;

    @GetMapping("/listproductdetailpageinfo")
    @ResponseBody
    public Map<String, Object> listProductDetailPageInfo(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        Long productId = HttpRequestUtil.getLong(request, "productId");
        Product product = null;
        if (productId != -1) {
            product = productService.queryProductByProductId(productId);
            modelMap.put("product", product);
            modelMap.put("success", true);
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", ProductStateEnum.PRODUCT_ID_EMPTY.getMsg());
        }
        return modelMap;
    }
}
