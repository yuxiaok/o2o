package com.o2o.entity;

import java.util.Date;

/**
 * @author kai.yu
 * @date 2019/4/24 顾客消费的商品映射
 **/
public class ProductSellDaily {
    private Long productSellDailyId;
    /**
     * 哪一天的销量，精确到天
     */
    private Date createTime;
    /**
     * 销量
     */
    private Integer total;
    /**
     * 商品信息实体类
     */
    private Product product;
    /**
     * 店铺信息实体类
     */
    private Shop shop;

    @Override
    public String toString() {
        return "ProductSellDaily{" + "createTime=" + createTime + ", total=" + total + ", product=" + product
            + ", shop=" + shop + '}';
    }

    public Long getProductSellDailyId() {
        return productSellDailyId;
    }

    public void setProductSellDailyId(Long productSellDailyId) {
        this.productSellDailyId = productSellDailyId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }
}
