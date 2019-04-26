package com.o2o.enums;

/**
 * @Author yukai
 * @Date 2018年9月5日
 */
public enum ProductCategoryEnum {
	INNER_ERROR(-2001,"插入当前店铺session失败"),
	SUCCESS(1,"操作成功"),
	EMPTY_LIST(-2002,"空集合"),
	;
	
	private int code;
	private String msg;
	private ProductCategoryEnum(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}
	public int getCode() {
		return code;
	}
	
	public String getMsg() {
		return msg;
	}
	
		
}
