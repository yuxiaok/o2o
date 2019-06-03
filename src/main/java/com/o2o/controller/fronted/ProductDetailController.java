package com.o2o.controller.fronted;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.o2o.entity.PersonInfo;
import com.o2o.entity.Product;
import com.o2o.enums.ProductStateEnum;
import com.o2o.service.ProductService;
import com.o2o.util.CodeUtil;
import com.o2o.util.HttpRequestUtil;
import com.o2o.util.ShortNetAddressUtil;

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
            PersonInfo user = (PersonInfo)request.getSession().getAttribute("user");
            if (user == null) {
                modelMap.put("needQRCode", false);
            } else {
                modelMap.put("needQRCode", true);
            }
            modelMap.put("product", product);
            modelMap.put("success", true);
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", ProductStateEnum.PRODUCT_ID_EMPTY.getMsg());
        }
        return modelMap;
    }

    public static String prefix;
    public static String suffix;
    public static String middle;
    public static String productmapUrl;

    @Value("${wechat.prefix}")
    public void setPrefix(String prefix) {
        ProductDetailController.prefix = prefix;
    }

    @Value("${wechat.suffix}")
    public void setSuffix(String suffix) {
        ProductDetailController.suffix = suffix;
    }

    @Value("${wechat.middle}")
    public void setMiddle(String middle) {
        ProductDetailController.middle = middle;
    }

    @Value("${wechat.productmap.url}")
    public void setProductmapUrl(String productmapUrl) {
        ProductDetailController.productmapUrl = productmapUrl;
    }

    @RequestMapping(value = "/generateqrcode4product", method = RequestMethod.GET)
    @ResponseBody
    public void generateQRCode4Product(HttpServletRequest request, HttpServletResponse response) {
        long productId = HttpRequestUtil.getLong(request, "productId");
        PersonInfo user = (PersonInfo)request.getSession().getAttribute("user");
        if (productId != -1 && user != null && user.getUserId() != null) {
            long timpStamp = System.currentTimeMillis();
            String content = "{aaaproductIdaaa:" + productId + ",aaacustomerIdaaa:" + user.getUserId()
                + ",aaacreateTimeaaa:" + timpStamp + "}";
            try {
                String longUrl = prefix + productmapUrl + middle + URLEncoder.encode(content, "UTF-8") + suffix;
                String shortUrl = ShortNetAddressUtil.getShortURL(longUrl);
                BitMatrix qRcodeImg = CodeUtil.generateQRCodeStream(shortUrl, response);
                MatrixToImageWriter.writeToStream(qRcodeImg, "png", response.getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
