package com.o2o.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.o2o.dao.ProductCategoryDao;
import com.o2o.dao.ProductDao;
import com.o2o.dto.ProductCategoryExecution;
import com.o2o.entity.ProductCategory;
import com.o2o.enums.ProductCategoryEnum;
import com.o2o.exceptions.ProductCategoryException;
import com.o2o.service.ProductCategoryService;

/**
 * @Author yukai
 * @Date 2018年9月5日
 */
@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {

    @Autowired
    private ProductCategoryDao productCategoryDao;

    @Autowired
    private ProductDao productDao;

    /**
     * @param shopId
     * @return
     */
    @Override
    public List<ProductCategory> getProductCategoryByShopId(long shopId) {
        return productCategoryDao.queryProductCategoryByShopId(shopId);
    }

    /**
     * @param list
     * @return
     */
    @Override
    @Transactional
    public ProductCategoryExecution batchProductCategory(List<ProductCategory> list) throws ProductCategoryException {
        if (list != null && list.size() > 0) {
            try {
                int effectNum = productCategoryDao.batchInsertProductCategory(list);
                if (effectNum <= 0) {
                    throw new ProductCategoryException("店铺类别创建失败");
                } else {
                    return new ProductCategoryExecution(ProductCategoryEnum.SUCCESS);
                }
            } catch (Exception e) {
                throw new ProductCategoryException(e.getMessage());
            }
        } else {
            return new ProductCategoryExecution(ProductCategoryEnum.EMPTY_LIST);
        }
    }

    /**
     * 删除商品类别
     * 
     * @param productCategory
     * @param shopId
     * @return
     */
    @Override
    @Transactional
    public ProductCategoryExecution deleteProductCategory(long productCategoryId, long shopId)
        throws ProductCategoryException {
        // 将该商品类别下的商品类别id置为空
        try {
            int effectNum = productDao.updateProductCategoryToNull(productCategoryId);
            if (effectNum < 0) {
                throw new ProductCategoryException("商品类别更新失败");
            }
        } catch (Exception e) {
            throw new ProductCategoryException("deleteProductCatrgory error:" + e.getMessage());
        }
        // 删除商品类别
        try {
            int effectNum = productCategoryDao.deleteProductCategory(productCategoryId, shopId);
            if (effectNum > 0) {
                return new ProductCategoryExecution(ProductCategoryEnum.SUCCESS);
            } else {
                throw new ProductCategoryException("商品类别删除失败");
            }
        } catch (Exception e) {
            throw new ProductCategoryException("deleteProductCatrgory error:" + e.getMessage());
        }
    }

}
