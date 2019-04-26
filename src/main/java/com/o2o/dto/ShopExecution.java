package com.o2o.dto;

import java.util.List;

import com.o2o.entity.Shop;
import com.o2o.enums.ShopStateEnum;

/**
 * @Author yukai
 * @Date 2018年7月14日
 */
public class ShopExecution {
	/**
	 * 返回的状态码
	 */
	private int state;
	/**
	 * 返回的状态信息
	 */
	private String stateInfo;
	/**
	 * 店铺数量
	 */
	private int count;
	/**
	 * 商铺（用于增删改查）
	 */
	private Shop shop;
	/**
	 * shop列表（查询店铺列表的时候使用）
	 */
	private List<Shop> shopList;
	
	public ShopExecution(){
		
	}
	
	/**
	 * 店铺生成失败时调用的构造方法
	 * @param stateEnum
	 */
	public ShopExecution(ShopStateEnum stateEnum){
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
	}
	
	/**
	 * 店铺操作成功时使用的构造器
	 * @param stateEnum
	 * @param shop
	 */
	public ShopExecution(ShopStateEnum stateEnum, Shop shop){
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
		this.shop = shop;
	}
	
	/**
	 * 店铺操作成功时使用的构造器(查询获取的店铺列表，返回给前端)
	 * @param stateEnum
	 * @param shop
	 */
	public ShopExecution(ShopStateEnum stateEnum,List<Shop> shopList){
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
		this.shopList = shopList;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getStateInfo() {
		return stateInfo;
	}

	public void setStateInfo(String stateInfo) {
		this.stateInfo = stateInfo;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public Shop getShop() {
		return shop;
	}

	public void setShop(Shop shop) {
		this.shop = shop;
	}

	public List<Shop> getShopList() {
		return shopList;
	}

	public void setShopList(List<Shop> shopList) {
		this.shopList = shopList;
	}
	
	
}
