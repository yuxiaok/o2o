package com.o2o.util;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;

/**
 * @Author yukai
 * @Date 2018年7月15日 验证工具类
 */
public class CodeUtil {
    /**
     * 二维码验证
     * 
     * @param request
     * @return
     */
    public static boolean checkVerifyCode(HttpServletRequest request) {
        // 获取Kaptcha生成的值
        String verifyCodeExpected =
            (String)request.getSession().getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
        // 获取用户输入的值
        String verifyCodeActual = HttpRequestUtil.getString(request, "verifyCodeActual");
        if (verifyCodeActual == null || (!verifyCodeExpected.equals(verifyCodeActual)))
            return false;
        return true;
    }

    /**
     * 生成二维码
     * 
     * @param content
     * @param response
     * @return
     */
    public static BitMatrix generateQRCodeStream(String content, HttpServletResponse response) {
        // 给响应添加头部信息，主要是告诉浏览器返回的是图片流
        response.setHeader("Cache-Control", "no-store");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/png");
        // 设置图片的文字编码以及内边框距
        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        hints.put(EncodeHintType.MARGIN, 0);
        BitMatrix bitMatrix;
        try {
            bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, 300, 300, hints);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return bitMatrix;
    }
}
