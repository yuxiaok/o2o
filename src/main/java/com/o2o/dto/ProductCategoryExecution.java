package com.o2o.dto;

import java.util.List;

import com.o2o.entity.ProductCategory;
import com.o2o.enums.ProductCategoryEnum;

/**
 * @Author yukai
 * @Date 2018年9月5日
 */
public class ProductCategoryExecution {
	//结果状态
	private int state;
	//结果标识
	private String stateInfo;
	private List<ProductCategory> list;
	
	public ProductCategoryExecution() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 失败时调用的构造器
	 * @param state
	 * @param stateInfo
	 */
	public ProductCategoryExecution(ProductCategoryEnum stateEnum) {
		this.state = stateEnum.getCode();
		this.stateInfo = stateEnum.getMsg();
	}
	
	/**
	 * 成功时调用的构造器
	 * @param state
	 * @param stateInfo
	 */
	public ProductCategoryExecution(ProductCategoryEnum stateEnum,List<ProductCategory> list) {
		this.state = stateEnum.getCode();
		this.stateInfo = stateEnum.getMsg();
		this.list = list;
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

	public List<ProductCategory> getList() {
		return list;
	}

	public void setList(List<ProductCategory> list) {
		this.list = list;
	}
	
	
}
