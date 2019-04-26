package com.o2o.util;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author yukai
 * @Date 2018年7月15日 验证码工具类
 */
public class CodeUtil {
	public static boolean checkVerifyCode(HttpServletRequest request) {
		//获取Kaptcha生成的值
		String verifyCodeExpected = (String) request.getSession()
				.getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
		//获取用户输入的值
		String verifyCodeActual = HttpRequestUtil.getString(request, "verifyCodeActual");
		if(verifyCodeActual==null ||(!verifyCodeExpected.equals(verifyCodeActual)))
			return false;
		return true;
	}
}
