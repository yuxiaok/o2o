package com.o2o.entity;

import java.util.Date;

/**
 * @Author yukai
 * @Date 2018年7月12日
 * 商家
 */
public class Shop {
	/**
	 * 商家id
	 */
	private Long shopId;
	/**
	 * 商家名称
	 */
	private String shopName;
	/**
	 * 门面图片
	 */
	private String shopImg;
	/**
	 * 商家地址
	 */
	private String shopAddr;
	/**
	 * 商家描述
	 */
	private String shopDesc;
	/**
	 * 联系地址
	 */
	private String phone;
	/**
	 * 状态 -1：不可用 0：审核 1：可用 
	 */
	private Integer enableStatus;
	/**
	 * 权重
	 */
	private Integer priority;
	
	private Date createTime;
	private Date lastEditTime;
	/**
	 * 超级管理员给店家的意见
	 */
	private String advice;
	/**
	 * 区域外键
	 */
	private Area area;
	/**
	 * 用户外键
	 */
	private PersonInfo personInfo;
	/**
	 * 类别外键
	 */
	private ShopCategory shopCategory;
	public Long getShopId() {
		return shopId;
	}
	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public String getShopImg() {
		return shopImg;
	}
	public void setShopImg(String shopImg) {
		this.shopImg = shopImg;
	}
	public String getShopAddr() {
		return shopAddr;
	}
	public void setShopAddr(String shopAddr) {
		this.shopAddr = shopAddr;
	}
	public String getShopDesc() {
		return shopDesc;
	}
	public void setShopDesc(String shopDesc) {
		this.shopDesc = shopDesc;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Integer getEnableStatus() {
		return enableStatus;
	}
	public void setEnableStatus(Integer enableStatus) {
		this.enableStatus = enableStatus;
	}
	public Integer getPriority() {
		return priority;
	}
	public void setPriority(Integer priority) {
		this.priority = priority;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getLastEditTime() {
		return lastEditTime;
	}
	public void setLastEditTime(Date lastEditTime) {
		this.lastEditTime = lastEditTime;
	}
	public String getAdvice() {
		return advice;
	}
	public void setAdvice(String advice) {
		this.advice = advice;
	}
	public Area getArea() {
		return area;
	}
	public void setArea(Area area) {
		this.area = area;
	}
	public PersonInfo getPersonInfo() {
		return personInfo;
	}
	public void setPersonInfo(PersonInfo personInfo) {
		this.personInfo = personInfo;
	}
	public ShopCategory getShopCategory() {
		return shopCategory;
	}
	public void setShopCategory(ShopCategory shopCategory) {
		this.shopCategory = shopCategory;
	}
	
}
