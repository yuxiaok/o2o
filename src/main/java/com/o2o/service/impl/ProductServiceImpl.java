package com.o2o.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.o2o.dao.ProductDao;
import com.o2o.dao.ProductImgDao;
import com.o2o.dto.ImageHolder;
import com.o2o.dto.ProductExecution;
import com.o2o.entity.Product;
import com.o2o.entity.ProductImg;
import com.o2o.enums.ProductStateEnum;
import com.o2o.exceptions.ProductOperationException;
import com.o2o.service.ProductService;
import com.o2o.util.ImageUtil;
import com.o2o.util.PageToRowUtil;
import com.o2o.util.PathUtil;

/**
 * @Author yukai
 * @Date 2018年9月6日
 */
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDao productDao;

    @Autowired
    private ProductImgDao productImgDao;

    /**
     * 增加商品
     * 
     * @param product
     * @param thuminal
     * @param productImgList
     * @return
     */
    @Override
    @Transactional
    public ProductExecution addProduct(Product product, ImageHolder thuminal, List<ImageHolder> productImgList)
        throws ProductOperationException {
        if (product != null && product.getShop() != null && product.getShop().getShopId() != null) {
            product.setCreateTime(new Date());
            product.setLastEditTime(new Date());
            product.setEnableStatus(1);
            // 增加缩略图
            if (thuminal != null) {
                addThuminal(product, thuminal);
            }

            try {
                int effectNum = productDao.insertProduct(product);
                if (effectNum <= 0) {
                    throw new ProductOperationException("创建商品失败");
                }

            } catch (Exception e) {
                throw new ProductOperationException(e.getMessage());
            }
            if (!productImgList.isEmpty()) {
                // 添加详情图
                addProductImgList(product, productImgList);
            }
            return new ProductExecution(ProductStateEnum.SUCCESS, product);

        } else {
            return new ProductExecution(ProductStateEnum.EMPTY);
        }
    }

    /**
     * 增加详情图
     * 
     * @param product
     * @param productImgList
     */
    private void addProductImgList(Product product, List<ImageHolder> productImgList) {
        // 创建相对文件夹
        String dest = PathUtil.getShopImagePath(product.getShop().getShopId());
        // 用于数据库添加
        List<ProductImg> list = new ArrayList<ProductImg>();
        for (ImageHolder i : productImgList) {
            ProductImg p = new ProductImg();
            String relative = ImageUtil.productImgThumbnail(i, dest);
            p.setImgAddr(relative);
            p.setProductId(product.getProductId());
            p.setCreateTime(new Date());
            list.add(p);
        }
        if (list.size() > 0) {
            try {
                int effecNum = productImgDao.batchInsertProductImg(list);
                if (effecNum <= 0) {
                    throw new ProductOperationException("创建商品详情图片失败");
                }
            } catch (Exception e) {
                throw new ProductOperationException("创建商品详情图片失败" + e.getMessage());
            }
        }
    }

    /**
     * 增加缩略图
     * 
     * @param product
     * @param thuminal
     */
    private void addThuminal(Product product, ImageHolder thuminal) {
        // 组成相对文件夹路径，不包含文件
        String dest = PathUtil.getShopImagePath(product.getShop().getShopId());
        // 组成相对地址，包含文件
        String relative = ImageUtil.generateThumbnail(thuminal, dest);
        product.setImgAddr(relative);

    }

    /**
     * 查找商品
     * 
     * @param productId
     * @return
     */
    @Override
    @Transactional
    public Product queryProductByProductId(long productId) {
        return productDao.queryProductByProductId(productId);
    }

    /**
     * 修改商品信息
     * 
     * @param product
     * @param thuminal
     * @param productImgList
     * @return 1.获取缩略图，如果有值，则先删除原有文件，在删除数据库 2.添加新的缩略图，并把相对地址赋值给product 3.获取详情图，如有值，删除原有文件，在删除数据库 4.更新product和product_img
     */
    @Override
    @Transactional
    public ProductExecution modifyProduct(Product product, ImageHolder thuminal, List<ImageHolder> productImgList) {
        // 空值判断
        if (product != null && product.getShop() != null && product.getShop().getShopId() != null) {
            // 给商品设置默认属性
            product.setLastEditTime(new Date());
            // 如商品缩略图不为空且原有缩略图不为空则删除原有缩略图并添加
            if (thuminal != null) {
                // 获取原有缩略图地址
                Product tempProduct = productDao.queryProductByProductId(product.getProductId());
                if (tempProduct.getImgAddr() != null) {
                    // 删除原有文件
                    ImageUtil.deleteFileOrPath(tempProduct.getImgAddr());
                }
                // 添加新缩略图并更新product
                addThuminal(product, thuminal);
            }

            // 如果详情图不为空,则删除原有的详情图,并添加新的图片
            if (productImgList != null && productImgList.size() > 0) {
                deleteProductImg(product.getProductId());
                addProductImgList(product, productImgList);
            }

            try {
                // 更新商品信息
                int effectNum = productDao.updateProduct(product);
                if (effectNum <= 0) {
                    throw new ProductOperationException("更新商品信息失败");
                } else {
                    return new ProductExecution(ProductStateEnum.SUCCESS, product);
                }
            } catch (Exception e) {
                throw new ProductOperationException("更新商品信息失败" + e.toString());
            }

        } else {
            return new ProductExecution(ProductStateEnum.EMPTY);
        }

    }

    /**
     * 删除详情图片
     * 
     * @param productId
     */
    private void deleteProductImg(long productId) {
        // 删除文件
        List<ProductImg> list = productImgDao.queryProductImgList(productId);
        for (ProductImg productImg : list) {
            ImageUtil.deleteFileOrPath(productImg.getImgAddr());
        }

        // 删除数据库里原有图片
        productImgDao.deleteProductImgByProductId(productId);
    }

    /**
     * 根据查询条件获取商品列表并分页，可输入的条件有：商品名（模糊），商品状态，店铺id，商品类别
     * 
     * @param productCondition
     *            条件
     * @param pageIndex
     *            页码
     * @param pageSize
     *            页大小
     * @return
     */
    @Override
    public ProductExecution getProductList(Product productContidion, int pageIndex, int pageSize) {
        // 将传过来的页码转换为数据库中的行码，并调用DAO层取回咋指定页码的商品列表
        int rowIndex = PageToRowUtil.pageToRow(pageIndex, pageSize);
        List<Product> queryProductList = productDao.queryProductList(productContidion, rowIndex, pageSize);
        // 在同样的条件获取数据总数
        int queryProductCount = productDao.queryProductCount(productContidion);
        // 封装
        ProductExecution productExecution = new ProductExecution();
        productExecution.setCount(queryProductCount);
        productExecution.setProductList(queryProductList);
        return productExecution;
    }

}
