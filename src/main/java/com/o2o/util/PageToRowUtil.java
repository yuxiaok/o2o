package com.o2o.util;

/**
 * @Author yukai
 * @Date 2018年7月22日
 */
public class PageToRowUtil {
	public static int pageToRow(int pageIndex,int pageSize){
		return (pageIndex>0)?(pageIndex-1)*pageSize:0;
	}
}
