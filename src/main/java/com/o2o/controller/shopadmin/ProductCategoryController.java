package com.o2o.controller.shopadmin;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.o2o.dto.ProductCategoryExecution;
import com.o2o.dto.Result;
import com.o2o.entity.ProductCategory;
import com.o2o.entity.Shop;
import com.o2o.enums.ProductCategoryEnum;
import com.o2o.service.ProductCategoryService;
import com.o2o.util.ResultUtil;

/**
 * @Author yukai
 * @Date 2018年9月5日
 */
@Controller
@RequestMapping("/shopadmin")
public class ProductCategoryController {
	
	@Autowired
	private ProductCategoryService productCategoryService;
	
	/**
	 * 商品分类
	* @param request
	* @return
	 */
	@GetMapping("getproductcategorylist")
	@ResponseBody
	public Result<List<ProductCategory>> getProductCategoryList(HttpServletRequest request){
		Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
		if(currentShop!=null&&currentShop.getShopId()>0){
			return ResultUtil.success(productCategoryService.getProductCategoryByShopId(currentShop.getShopId()));
		}else{
			return ResultUtil.error(ProductCategoryEnum.INNER_ERROR.getCode(), ProductCategoryEnum.INNER_ERROR.getMsg());
		}
		
	}
	
	@PostMapping("addproductcategorys")
	@ResponseBody
	public Map<String, Object> addProductCategorys(@RequestBody List<ProductCategory> productCategoryList,
			HttpServletRequest request){
		Map<String, Object> map = new HashMap<String, Object>();
		Shop shop = (Shop) request.getSession().getAttribute("currentShop");
		for(ProductCategory p:productCategoryList){
			p.setShopId(shop.getShopId());
			p.setCreateTime(new Date());
		}
		if(productCategoryList!=null&&productCategoryList.size()>0){
			try {
				ProductCategoryExecution pce = productCategoryService.batchProductCategory(productCategoryList);
				if(pce.getState()== ProductCategoryEnum.SUCCESS.getCode()){
					map.put("success",true);
				}else{
					map.put("success", false);
					map.put("errMsg", pce.getStateInfo());
				}
			} catch (RuntimeException e) {
				map.put("success", false);
				map.put("errMsg", e.getMessage());
				return map;
			}
		}else{
			map.put("success", false);
			map.put("errMsg", "请至少输入一个商品类别");
		}
		return map;
	}
	
	@PostMapping("removeproductcategory")
	@ResponseBody
	public Map<String, Object> removeProductCategory(Long productCategoryId,
			HttpServletRequest request){
		Map<String, Object> map = new HashMap<String, Object>();	
		if(productCategoryId!=null&&productCategoryId>0){
			try {
				Shop shop = (Shop) request.getSession().getAttribute("currentShop");
				ProductCategoryExecution pce = productCategoryService.deleteProductCategory(productCategoryId, shop.getShopId());
				if(pce.getState()== ProductCategoryEnum.SUCCESS.getCode()){
					map.put("success",true);
				}else{
					map.put("success", false);
					map.put("errMsg", pce.getStateInfo());
				}
			} catch (RuntimeException e) {
				map.put("success", false);
				map.put("errMsg", e.getMessage());
				return map;
			}
		}else{
			map.put("success", false);
			map.put("errMsg", "请至少输入一个商品类别");
		}
		return map;
	}
}
