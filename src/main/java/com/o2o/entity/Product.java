package com.o2o.entity;

import java.util.Date;
import java.util.List;

/**
 * @Author yukai
 * @Date 2018年7月12日 商品
 */
public class Product {
    /**
     * 商品id
     */
    private Long productId;
    /**
     * 商品名称
     */
    private String productName;
    /**
     * 商品描述
     */
    private String productDesc;
    /**
     * 缩略
     */
    private String imgAddr;
    /**
     * 原价
     */
    private String normalPrice;
    /**
     * 折扣价
     */
    private String promotionPrice;
    /**
     * 权重
     */
    private Integer priority;
    /**
     * 商品积分
     */
    private Integer point;
    private Date createTime;
    private Date lastEditTime;
    /**
     * 0：下架 1：在架
     */
    private Integer enableStatus;

    /**
     * 商品图片（1对多）
     */
    private List<ProductImg> productImgList;
    /**
     * 商品类别（1对1）
     */
    private ProductCategory productCategory;
    /**
     * 商家（1对1）
     */
    private Shop shop;

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDesc() {
        return productDesc;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }

    public String getImgAddr() {
        return imgAddr;
    }

    public void setImgAddr(String imgAddr) {
        this.imgAddr = imgAddr;
    }

    public String getNormalPrice() {
        return normalPrice;
    }

    public void setNormalPrice(String normalPrice) {
        this.normalPrice = normalPrice;
    }

    public String getPromotionPrice() {
        return promotionPrice;
    }

    public void setPromotionPrice(String promotionPrice) {
        this.promotionPrice = promotionPrice;
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

    public Integer getEnableStatus() {
        return enableStatus;
    }

    public void setEnableStatus(Integer enableStatus) {
        this.enableStatus = enableStatus;
    }

    public List<ProductImg> getProductImgList() {
        return productImgList;
    }

    public void setProductImgList(List<ProductImg> productImgList) {
        this.productImgList = productImgList;
    }

    public ProductCategory getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(ProductCategory productCategory) {
        this.productCategory = productCategory;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    @Override
    public String toString() {
        return "Product [productId=" + productId + ", productName=" + productName + ", productDesc=" + productDesc
            + ", imgAddr=" + imgAddr + ", normalPrice=" + normalPrice + ", promotionPrice=" + promotionPrice
            + ", priority=" + priority + ", createTime=" + createTime + ", lastEditTime=" + lastEditTime
            + ", enableStatus=" + enableStatus + ", productImgList=" + productImgList + ", productCategory="
            + productCategory + ", shop=" + shop + "]";
    }

}
