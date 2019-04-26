package com.o2o.dto;

import java.util.List;

import com.o2o.entity.Product;
import com.o2o.enums.ProductStateEnum;

/**
 * @Author yukai
 * @Date 2018年9月6日
 */
public class ProductExecution {
    private int state;
    private String stateInfo;
    /**
     * 商品数量
     */
    private int count;

    /**
     * 查看某个商品时
     */
    private Product product;

    /**
     * 查看商品列表
     */
    private List<Product> productList;

    public ProductExecution() {

    }

    /**
     * 失败时的调用
     * 
     * @param productStateEnum
     */
    public ProductExecution(ProductStateEnum productStateEnum) {
        this.state = productStateEnum.getCode();
        this.stateInfo = productStateEnum.getMsg();
    }

    /**
     * 成功时的调用
     * 
     * @param productStateEnum
     */
    public ProductExecution(ProductStateEnum productStateEnum, Product product) {
        this.state = productStateEnum.getCode();
        this.stateInfo = productStateEnum.getMsg();
        this.product = product;
    }

    /**
     * 成功时的调用
     * 
     * @param productStateEnum
     */
    public ProductExecution(ProductStateEnum productStateEnum, List<Product> productList) {
        this.state = productStateEnum.getCode();
        this.stateInfo = productStateEnum.getMsg();
        this.productList = productList;
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

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

}
