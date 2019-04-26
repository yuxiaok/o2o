package com.o2o.entity;

import java.util.Date;

/**
 * @Author yukai
 * @Date 2018年7月12日
 * 商品类别
 */
public class ProductCategory {
	/**
	 * 商品类别id
	 */
	 private Long productCategoryId;
	 /**
	  * 商家id（这个类别对应的是哪个商家的）
	  */
	 private Long shopId;
	 /**
	  * 商品类别名称
	  */
	 private String productCategoryName;
	 /**
	  * 权重
	  */
	 private Integer priority;
	 private Date createTime;
	public Long getProductCategoryId() {
		return productCategoryId;
	}
	public void setProductCategoryId(Long productCategoryId) {
		this.productCategoryId = productCategoryId;
	}
	public Long getShopId() {
		return shopId;
	}
	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}
	public String getProductCategoryName() {
		return productCategoryName;
	}
	public void setProductCategoryName(String productCategoryName) {
		this.productCategoryName = productCategoryName;
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
	 
	 
}
