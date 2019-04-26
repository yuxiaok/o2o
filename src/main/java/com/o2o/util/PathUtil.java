package com.o2o.util;

/**
 * @Author yukai
 * @Date 2018年7月14日
 */
public class PathUtil {
	//获取文件分隔符
	//private static String sepertor = System.getProperty("file.name");
	/**
	 * 设置图片存放的根路径
	* @return
	 */
	public static String getImgBasePath(){
		//获取电脑的系统
		String os = System.getProperty("os.name");
		String basePath = "";
		if(os.toLowerCase().startsWith("win")){
			basePath = "d:/projectdev/image/";
		}else {
			basePath = "/Users/baidu/work/image";
		}
		//basePath = basePath.replace("/", sepertor);
		return basePath;
	}
	
	/**
	 * 设置店铺图片的相对路径
	* @param shopId
	* @return
	 */
	public static String getShopImagePath (long shopId) {
		String imagePath = "/upload/item/shop/" + shopId +"/";
		return imagePath;
	}
}
