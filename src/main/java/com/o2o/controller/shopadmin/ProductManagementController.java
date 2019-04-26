package com.o2o.controller.shopadmin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.o2o.dto.ImageHolder;
import com.o2o.dto.ProductExecution;
import com.o2o.entity.Product;
import com.o2o.entity.ProductCategory;
import com.o2o.entity.Shop;
import com.o2o.enums.ProductStateEnum;
import com.o2o.exceptions.ProductOperationException;
import com.o2o.service.ProductCategoryService;
import com.o2o.service.ProductService;
import com.o2o.util.CodeUtil;
import com.o2o.util.HttpRequestUtil;

/**
 * @Author yukai
 * @Date 2018年9月19日
 */
@Controller
@RequestMapping("/shopadmin")
public class ProductManagementController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductCategoryService productCategoryService;

    private static final int IMAGEMAXCOUNT = 6;

    /**
     * 增加商品
     * 
     * @param request
     * @return
     * @throws IOException
     */
    @PostMapping("/addproduct")
    @ResponseBody
    public Map<String, Object> addProduct(HttpServletRequest request) throws IOException {
        Map<String, Object> map = new HashMap<String, Object>();
        // 验证码校验
        if (!CodeUtil.checkVerifyCode(request)) {
            map.put("success", false);
            map.put("errMsg", "输入了错误的验证码");
            return map;
        }

        // 接受前端参数的变量的初始化，包括商品，缩略图，详情图列表实体类
        ObjectMapper mapper = new ObjectMapper();
        Product product = null;
        String productStr = HttpRequestUtil.getString(request, "productStr");
        MultipartHttpServletRequest multipartRequest = null;
        ImageHolder thumbnail = null;
        List<ImageHolder> productImgList = new ArrayList<ImageHolder>();
        CommonsMultipartResolver multipartResolver =
            new CommonsMultipartResolver(request.getSession().getServletContext());
        try {
            // 若请求中存在文件流，则取出相关的文件（包括缩略图和详情图）
            if (multipartResolver.isMultipart(request)) {
                thumbnail = handleImage(request, productImgList);
            } else {
                map.put("success", false);
                map.put("errMsg", "上传图片不能为空");
                return map;
            }
        } catch (Exception e) {
            map.put("success", false);
            map.put("errMsg", e.toString());
            return map;
        }

        try {
            // 尝试获取前端传过来的表单string流，将其转换成product实体类
            product = mapper.readValue(productStr, Product.class);
        } catch (Exception e) {
            map.put("success", false);
            map.put("errMsg", e.toString());
            return map;
        }

        // 若product信息，缩略图以及详情图列表为非空，则开始进行商品添加操作
        if (product != null && thumbnail != null && productImgList.size() > 0) {
            try {
                // 从session中获取当前店铺的id并赋值给product,减少对前端数据的依赖
                Shop currentShop = (Shop)request.getSession().getAttribute("currentShop");
                product.setShop(currentShop);
                // 执行添加操作
                ProductExecution pe = productService.addProduct(product, thumbnail, productImgList);
                if (pe.getState() == ProductStateEnum.SUCCESS.getCode()) {
                    map.put("success", true);
                } else {
                    map.put("success", false);
                    map.put("errMsg", pe.getStateInfo());
                }
            } catch (ProductOperationException e) {
                map.put("success", false);
                map.put("errMsg", e.toString());
                return map;
            }
        } else {
            map.put("success", false);
            map.put("errMsg", "请输入商品信息");
        }
        return map;
    }

    /**
     * 获取商品信息
     * 
     * @param productId
     * @return
     */
    @GetMapping("/getproductbyid")
    @ResponseBody
    public Map<String, Object> getProductById(@RequestParam long productId) {
        Map<String, Object> map = new HashMap<>();
        if (productId > -1) {
            // 获取该商品
            Product product = productService.queryProductByProductId(productId);
            // 获取该商铺下的所有商品类别
            List<ProductCategory> pcList =
                productCategoryService.getProductCategoryByShopId(product.getShop().getShopId());
            map.put("success", true);
            map.put("product", product);
            map.put("productCategoryList", pcList);

        } else {
            map.put("success", false);
            map.put("errMsg", "参数错误");
        }
        return map;
    }

    /**
     * 修改商品信息
     * 
     * @param request
     * @return
     */
    @PostMapping("/modifyProduct")
    @ResponseBody
    public Map<String, Object> modifyProduct(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        // 该标识用来判断是整个修改还是只是对商品下架
        // 只对商品下架时是不需要验证码的
        boolean statusChange = HttpRequestUtil.getBoolean(request, "statusChange");
        // 整个商品修改
        if (!statusChange && !CodeUtil.checkVerifyCode(request)) {
            map.put("success", false);
            map.put("errMsg", "验证码错误");
            return map;
        }

        // 接受前台参数的变量的初始化，商品，缩略图，详情图
        ObjectMapper mapper = new ObjectMapper();
        Product product = null;
        ImageHolder thumbnail = null;
        List<ImageHolder> productImgList = new ArrayList<>();

        // 文件上传视图解析
        CommonsMultipartResolver multipartResolver =
            new CommonsMultipartResolver(request.getSession().getServletContext());
        try {
            if (multipartResolver.isMultipart(request)) {
                // 调用处理缩略图和详情图的方法
                thumbnail = handleImage(request, productImgList);
            }
        } catch (Exception e) {
            map.put("success", false);
            map.put("errMsg", e.toString());
            return map;
        }

        try {
            // 获取前台传过来的product并转化为对象
            String productStr = HttpRequestUtil.getString(request, "productStr");
            product = mapper.readValue(productStr, Product.class);
        } catch (IOException e) {
            map.put("success", false);
            map.put("errMsg", e.toString());
            return map;
        }

        // 空值判断
        if (product != null) {
            try {
                // 从session中获取店铺id，并赋值给product,减少对前端数据的依赖。
                Shop currentShop = (Shop)request.getSession().getAttribute("currentShop");
                product.setShop(currentShop);

                // 进行修改
                ProductExecution pe = productService.modifyProduct(product, thumbnail, productImgList);
                if (pe.getState() == ProductStateEnum.SUCCESS.getCode()) {
                    map.put("success", true);
                } else {
                    map.put("success", false);
                    map.put("errMsg", pe.getStateInfo());
                }
            } catch (RuntimeException e) {
                map.put("success", false);
                map.put("errMsg", e.toString());
                return map;
            }

        } else {
            map.put("success", false);
            map.put("errMsg", "请输入商品信息");
        }
        return map;
    }


    @GetMapping("/getproductlistbyshop")
    @ResponseBody
    public Map<String, Object> getProductListByShop(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        // 获取从前台传过来的页码
        int pageIndex = HttpRequestUtil.getInt(request, "pageIndex");
        // 获取前台传过来的每页要求返回的商品数量
        int pageSize = HttpRequestUtil.getInt(request, "pageSize");
        // 从当前店铺中获取session信息，主要获取商铺id
        Shop currentShop = (Shop)request.getSession().getAttribute("currentShop");
        // 空值判断
        if ((pageIndex > -1) && (pageSize > -1) && (currentShop != null) && (currentShop.getShopId() != null)) {
            // 获取传入的需要检索的条件，包括是否需要从某个商品类别以及模糊查找商品名去筛选某个店铺下的商品列表
            // 筛选的条件可以进行排列组合
            long productCategoryId = HttpRequestUtil.getLong(request, "productCategoryId");
            String productName = HttpRequestUtil.getString(request, "productName");
            // 封装条件
            Product productCondition = compactProductCondition(currentShop.getShopId(), -1L, productName);
            // 传入查询条件以及分页信息进行查询，返回相应商品列表以及总数
            ProductExecution productExecution = productService.getProductList(productCondition, pageIndex, pageSize);
            map.put("productList", productExecution.getProductList());
            map.put("count", productExecution.getCount());
            map.put("success", true);
        } else {
            map.put("success", false);
            map.put("errMsg", "empty pageIndex or pageSize or shopId");
        }
        return map;
    }

    /**
     * 封装查询列表的条件
     * 
     * @param shopId
     * @param productCategoryId
     * @param productName
     * @return
     */
    private Product compactProductCondition(long shopId, long productCategoryId, String productName) {
        Product productCondition = new Product();
        // 商品是一定要有的，从session中获取的
        Shop shop = new Shop();
        shop.setShopId(shopId);
        productCondition.setShop(shop);
        // 如果指定了商品类别
        if (productCategoryId != -1L) {
            ProductCategory productCategory = new ProductCategory();
            productCategory.setProductCategoryId(productCategoryId);
            productCondition.setProductCategory(productCategory);
        }
        // 如果指定了商品名称
        if (productName != null) {
            productCondition.setProductName(productName);
        }
        return productCondition;
    }

    /**
     * 处理缩略图和详情图的方法
     * 
     * @param request
     * @param productImgList
     * @return
     * @throws IOException
     */
    private ImageHolder handleImage(HttpServletRequest request, List<ImageHolder> productImgList) throws IOException {
        MultipartHttpServletRequest multipartRequest;
        ImageHolder thumbnail;
        multipartRequest = (MultipartHttpServletRequest)request;
        CommonsMultipartFile thumbnailFile = (CommonsMultipartFile)multipartRequest.getFile("thumbnail");
        thumbnail = new ImageHolder(thumbnailFile.getOriginalFilename(), thumbnailFile.getInputStream());
        // 取出详情图列表并构建List<ImageHolder>列表对象，最多支持六张图片上传
        for (int i = 0; i < IMAGEMAXCOUNT; i++) {
            // 若取出的第i个详情图片文件流不为空，则将其加入详情图列表
            CommonsMultipartFile productImgFile = (CommonsMultipartFile)multipartRequest.getFile("productImg" + i);
            if (productImgFile != null) {
                ImageHolder productImg =
                    new ImageHolder(productImgFile.getOriginalFilename(), productImgFile.getInputStream());
                productImgList.add(productImg);
            } else {
                // 若取出的弟i个详情图片文件流为空，则终止循环
                break;
            }
        }
        return thumbnail;
    }
}
