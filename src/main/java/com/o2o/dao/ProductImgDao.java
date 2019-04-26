package com.o2o.dao;

import java.util.List;

import com.o2o.entity.ProductImg;

/**
 * @Author yukai
 * @Date 2018年9月6日
 */
public interface ProductImgDao {
	/**
	 * 批量插入产品图片
	* @param productImgs
	* @return
	 */
	public int batchInsertProductImg(List<ProductImg> productImgs);
	
	/**
	 * 
	* @param productId
	* @return
	 */
	public int deleteProductImgByProductId(long productId);
	
	/**
	 * 获取该产品的所有详情图
	* @param productId
	* @return
	 */
	public List<ProductImg> queryProductImgList(long productId);
}
