package com.o2o.controller.fronted;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.o2o.dto.UserProductMapExecution;
import com.o2o.entity.PersonInfo;
import com.o2o.entity.Product;
import com.o2o.entity.Shop;
import com.o2o.entity.UserProductMap;
import com.o2o.service.UserProductMapService;
import com.o2o.util.HttpRequestUtil;

@Controller
@RequestMapping("/fronted")
public class MyProductController {
    @Autowired
    private UserProductMapService userProductMapService;

    @RequestMapping(value = "/listuserproductmapsbycustomer", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> listUserProductMapsByCustomer(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        int pageIndex = HttpRequestUtil.getInt(request, "pageIndex");
        int pageSize = HttpRequestUtil.getInt(request, "pageSize");
        // todo
        PersonInfo user = (PersonInfo)request.getSession().getAttribute("user");
        if ((pageIndex > -1) && (pageSize > -1) && (user != null) && (user.getUserId() != -1)) {
            UserProductMap userProductMapCondition = new UserProductMap();
            userProductMapCondition.setUser(user);
            long shopId = HttpRequestUtil.getLong(request, "shopId");
            if (shopId > -1) {
                Shop shop = new Shop();
                shop.setShopId(shopId);
                userProductMapCondition.setShop(shop);
            }
            String productName = HttpRequestUtil.getString(request, "productName");
            if (productName != null) {
                Product product = new Product();
                product.setProductName(productName);
                userProductMapCondition.setProduct(product);
            }
            UserProductMapExecution ue =
                userProductMapService.listUserProductMap(userProductMapCondition, pageIndex, pageSize);
            modelMap.put("userProductMapList", ue.getUserProductMapList());
            modelMap.put("count", ue.getCount());
            modelMap.put("success", true);
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "empty pageSize or pageIndex or shopId");
        }
        return modelMap;
    }
}
