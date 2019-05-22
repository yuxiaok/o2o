package com.o2o.controller.shopadmin;

import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.o2o.entity.Shop;
import com.o2o.util.CodeUtil;
import com.o2o.util.ShortNetAddressUtil;

@Controller
@RequestMapping("/shopadmin")
public class ShopQRCodeController {
    private static String prefix;
    private static String suffix;
    private static String middle;
    private static String authUrl;

    @Value("${wechat.prefix}")
    public void setPrefix(String prefix) {
        ShopQRCodeController.prefix = prefix;
    }

    @Value("${wechat.suffix}")
    public void setSuffix(String suffix) {
        ShopQRCodeController.suffix = suffix;
    }

    @Value("${wechat.middle}")
    public void setMiddle(String middle) {
        ShopQRCodeController.middle = middle;
    }

    @Value("${wechat.auth.url}")
    public void setAuthUrl(String authUrl) {
        ShopQRCodeController.authUrl = authUrl;
    }

    /**
     * 生成二维码
     *
     * @param request
     * @param response
     */
    @GetMapping("/generateqrcode4shopauth")
    @ResponseBody
    public void generateQRCode4ShopAuth(HttpServletRequest request, HttpServletResponse response) {
        Shop currentShop = (Shop)request.getSession().getAttribute("currentShop");
        if (currentShop != null && currentShop.getShopId() != null) {
            // 获取时间戳，设置二维码的有效时间
            long timeStamp = System.currentTimeMillis();
            String content = "{aaashopIdaaa:" + currentShop.getShopId() + ",aaacreateTimeaaa:" + timeStamp + "}";
            try {
                // 拼接微信的访问链接
                String longUrl = prefix + authUrl + middle + URLEncoder.encode(content, "UTF-8") + suffix;
                // 将长链接转为短链接
                String shortURL = ShortNetAddressUtil.getShortURL(longUrl);
                // 生成二维码图片
                BitMatrix bitMatrix = CodeUtil.generateQRCodeStream(shortURL, response);
                MatrixToImageWriter.writeToStream(bitMatrix, "png", response.getOutputStream());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
