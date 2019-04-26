package com.o2o.enums;

/**
 * @Author yukai
 * @Date 2018年7月14日
 */
public enum ShopStateEnum {
	CHECK(0,"审核中"),
	OFFLINE(-1,"非法店铺"),
	SUCCESS(1,"操作成功"),
	PASS(2,"通过认证"),
	INNER_ERROR(-1001,"内部系统错误"),
	NULL_SHOPID(-1002,"ShopId为空"),
	SHOP_ENPTY(-1003,"店铺为空"),
	;
	private int state;
	private String stateInfo;
	
	private ShopStateEnum(int state,String stateInfo){
		this.state = state;
		this.stateInfo = stateInfo;
	}
	
	/**
	 * 根据状态码获取该Enum
	* @param state
	* @return
	 */
	public static ShopStateEnum getShopStateEnum(int state){
		for (ShopStateEnum stateEnum : ShopStateEnum.values()) {
			if (state == stateEnum.getState()) 
				return stateEnum;
		}
		return null;
	}

	public int getState() {
		return state;
	}
	public String getStateInfo() {
		return stateInfo;
	}


}
