package com.o2o.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @Author yukai
 * @Date 2018年7月14日
 */
@Configuration
public class PathUtil {
    /**
     * // 获取文件分隔符
     */
    private static String sepertor = System.getProperty("file.separator");

    /**
     * windows地址
     */
    private static String winPath;
    /**
     * linux地址
     */
    private static String linuxPath;
    /**
     * 相对地址
     */
    private static String revelantPath;

    @Value("${win.base.path}")
    public void setWinPath(String winPath) {
        PathUtil.winPath = winPath;
    }

    @Value("${linux.base.path}")
    public void setLinuxPath(String linuxPath) {
        PathUtil.linuxPath = linuxPath;
    }

    @Value("${shop.relevant.path}")
    public void setRevelantPath(String revelantPath) {
        PathUtil.revelantPath = revelantPath;
    }

    /**
     * 设置图片存放的根路径
     * 
     * @return
     */
    // TODO
    public static String getImgBasePath() {
        // 获取电脑的系统
        String os = System.getProperty("os.name").toLowerCase();
        String basePath = "";
        if (os.startsWith("win")) {
            basePath = winPath;
        } else {
            basePath = linuxPath;
        }
        basePath = basePath.replace("/", sepertor);
        return basePath;
    }

    /**
     * 设置店铺图片的相对路径
     * 
     * @param shopId
     * @return
     */
    public static String getShopImagePath(long shopId) {
        String imagePath = revelantPath + shopId + sepertor;
        return imagePath.replace("/", sepertor);
    }
}
