package com.o2o.entity;

import java.util.Date;

/**
 * @Author yukai
 * @Date 2018年7月12日
 * 产品图片(与产品是一对多的关系)
 */
public class ProductImg {
	/**
	 * 图片id
	 */
	private Long productImgId;
	/**
	 * 图片路径
	 */
	private String imgAddr;
	/**
	 * 图片描述
	 */
	private String imgDesc;
	/**
	 * 权重
	 */
	private Integer priority;
	private Date createTime;
	/**
	 * 产品id
	 */
	private Long productId;
	public Long getProductImgId() {
		return productImgId;
	}
	public void setProductImgId(Long productImgId) {
		this.productImgId = productImgId;
	}
	public String getImgAddr() {
		return imgAddr;
	}
	public void setImgAddr(String imgAddr) {
		this.imgAddr = imgAddr;
	}
	public String getImdDesc() {
		return imgDesc;
	}
	public void setImdDesc(String imgDesc) {
		this.imgDesc = imgDesc;
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
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	
	
}
