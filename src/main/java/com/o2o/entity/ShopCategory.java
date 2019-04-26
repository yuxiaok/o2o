package com.o2o.entity;

import java.util.Date;

/**
 * @Author yukai
 * @Date 2018年7月12日
 * 商家类别
 */
public class ShopCategory {
	/**
	 * 商家类别id
	 */
	private Long shopCategoryId;
	/**
	 * 商家类别
	 */
	private String shopCategoryName;
	/**
	 * 类别描述
	 */
	private String shopCategoryDesc;
	/**
	 * 类别图片
	 */
	private String shopCategoryImg;
	/**
	 * 权重
	 */
	private Integer priority;
	/**
	 * 上一级类别id
	 */
	private ShopCategory parent;
	private Date createTime;
	private Date lastEditTime;
	public Long getShopCategoryId() {
		return shopCategoryId;
	}
	public void setShopCategoryId(Long shopCategoryId) {
		this.shopCategoryId = shopCategoryId;
	}
	public String getShopCategoryName() {
		return shopCategoryName;
	}
	public void setShopCategoryName(String shopCategoryName) {
		this.shopCategoryName = shopCategoryName;
	}
	public String getShopCategoryDesc() {
		return shopCategoryDesc;
	}
	public void setShopCategoryDesc(String shopCategoryDesc) {
		this.shopCategoryDesc = shopCategoryDesc;
	}
	public String getShopCategoryImg() {
		return shopCategoryImg;
	}
	public void setShopCategoryImg(String shopCategoryImg) {
		this.shopCategoryImg = shopCategoryImg;
	}
	public Integer getPriority() {
		return priority;
	}
	public void setPriority(Integer priority) {
		this.priority = priority;
	}
	public ShopCategory getParent() {
		return parent;
	}
	public void setParent(ShopCategory parent) {
		this.parent = parent;
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
	@Override
	public String toString() {
		return "ShopCategory [shopCategoryId=" + shopCategoryId + ", shopCategoryName=" + shopCategoryName
				+ ", shopCategoryDesc=" + shopCategoryDesc + ", shopCategoryImg=" + shopCategoryImg + ", priority="
				+ priority + ", parent=" + parent + ", createTime=" + createTime + ", lastEditTime=" + lastEditTime
				+ "]";
	}
	
	
}
