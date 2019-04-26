package com.o2o.util;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author yukai
 * @Date 2018年7月14日
 */
public class HttpRequestUtil {
    public static int getInt(HttpServletRequest httpServletRequest, String key) {
        if (null == httpServletRequest.getParameter(key)|| "".equals(httpServletRequest.getParameter(key))){
            return -1;
        }
        return Integer.valueOf(httpServletRequest.getParameter(key));
    }

    public static Double getDouble(HttpServletRequest httpServletRequest, String key) {
        return Double.valueOf(httpServletRequest.getParameter(key));
    }

    public static Long getLong(HttpServletRequest httpServletRequest, String key) {
        if (null == httpServletRequest.getParameter(key)|| "".equals(httpServletRequest.getParameter(key))){
            return -1L;
        }
        return Long.valueOf(httpServletRequest.getParameter(key));
    }

    public static Boolean getBoolean(HttpServletRequest httpServletRequest, String key) {
        return Boolean.valueOf(httpServletRequest.getParameter(key));
    }

    public static String getString(HttpServletRequest httpServletRequest, String key) {
        String result = httpServletRequest.getParameter(key);
        if (result != null) {
            // 去除两端空格
            result = result.trim();
        }
        if ("".equals(result)) {
            result = null;
        }
        return result;
    }
}
