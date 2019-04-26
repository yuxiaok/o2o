package com.o2o.controller.shopadmin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.o2o.dto.ImageHolder;
import com.o2o.dto.ShopExecution;
import com.o2o.entity.Area;
import com.o2o.entity.PersonInfo;
import com.o2o.entity.Shop;
import com.o2o.entity.ShopCategory;
import com.o2o.enums.ShopStateEnum;
import com.o2o.service.AreaService;
import com.o2o.service.ShopCategoryService;
import com.o2o.service.ShopService;
import com.o2o.util.CodeUtil;
import com.o2o.util.HttpRequestUtil;

/**
 * @Author yukai
 * @Date 2018年7月14日
 */
@Controller
@RequestMapping("/shopadmin")
public class ShopManagementController {

    @Autowired
    private ShopService shopService;

    @Autowired
    private ShopCategoryService shopCategoryService;

    @Autowired
    private AreaService areaService;

    /**
     * 商品信息管理
     * 
     * @param request
     * @return
     */
    @GetMapping("/getshopmanagementinfo")
    @ResponseBody
    public Map<String, Object> getShopManagementInfo(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        long shopId = HttpRequestUtil.getLong(request, "shopId");
        if (shopId <= 0) {
            Object currentShopObj = request.getSession().getAttribute("currentShop");
            if (currentShopObj == null) {
                map.put("redirect", true);
                map.put("url", "/o2o/shopadmin/shoplist");
            } else {
                Shop currentShop = (Shop)currentShopObj;
                map.put("redirect", false);
                map.put("shopId", currentShop.getShopId());
            }
        } else {
            Shop currentShop = new Shop();
            currentShop.setShopId(shopId);
            request.getSession().setAttribute("currentShop", currentShop);
            map.put("redirect", false);
        }
        return map;
    }

    /**
     * 获取商铺列表
     * 
     * @param request
     * @return
     */
    @GetMapping("/getshoplist")
    @ResponseBody
    public Map<String, Object> getShopList(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<String, Object>();
        // TODO 删除
        PersonInfo /*user = new PersonInfo();
                   user.setUserId(1L);
                   user.setUserName("test");
                   request.getSession().setAttribute("user", user);*/
        user = (PersonInfo)request.getSession().getAttribute("user");
        try {
            Shop shopCondition = new Shop();
            shopCondition.setPersonInfo(user);
            ShopExecution se = shopService.getShopList(shopCondition, 0, 100);
            map.put("shopList", se.getShopList());
            // 列出店铺成功之后，将店铺放入session中作为权限验证依据，即该账号只能操作自己的店铺
            request.getSession().setAttribute("shopList", se.getShopList());
            map.put("user", user);
            map.put("success", true);
        } catch (Exception e) {
            map.put("success", false);
            map.put("errMsg", e.getMessage());
        }
        return map;
    }

    /**
     * 获取店铺类别和区域
     * 
     * @return
     */
    @GetMapping("/getshopinitinfo")
    @ResponseBody
    public Map<String, Object> getShopInitInfo() {
        Map<String, Object> map = new HashMap<>();
        try {
            List<ShopCategory> shopCategoryList = shopCategoryService.getShopCategoryList(new ShopCategory());
            List<Area> areaList = areaService.getAreaList();
            map.put("shopCategoryList", shopCategoryList);
            map.put("areaList", areaList);
            map.put("success", true);
        } catch (Exception e) {
            map.put("success", false);
            map.put("errMsg", e.getMessage());
            return map;
        }
        return map;
    }

    /**
     * 注册商铺
     * 
     * @param httpServletRequest
     * @param key
     * @return
     */
    @PostMapping("/registershop")
    @ResponseBody
    public Map<String, Object> registerShop(HttpServletRequest httpServletRequest) {
        Map<String, Object> map = new HashMap<>();
        if (!CodeUtil.checkVerifyCode(httpServletRequest)) {
            map.put("success", false);
            map.put("errMsg", "输入了错误的验证码");
            return map;
        }
        // 1.接收并转化相应的参数，包括店铺信息以及图片信息
        // 将表单提交的内容转化为string类型
        String shopStr = HttpRequestUtil.getString(httpServletRequest, "shopStr");
        ObjectMapper mapper = new ObjectMapper();
        Shop shop = null;
        try {
            // 通过jackson-databind将string类型，转为实体类
            shop = mapper.readValue(shopStr, Shop.class);
        } catch (Exception e) {
            map.put("success", false);
            map.put("errMsg", e.getMessage());
            return map;
        }
        // 获取文件
        CommonsMultipartFile shopImg = null;
        CommonsMultipartResolver commonsMultipartResolver =
            new CommonsMultipartResolver(httpServletRequest.getSession().getServletContext());
        if (commonsMultipartResolver.isMultipart(httpServletRequest)) {
            MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest)httpServletRequest;
            shopImg = (CommonsMultipartFile)multipartHttpServletRequest.getFile("shopImg");
        } else {
            map.put("success", false);
            map.put("errMsg", "上传图片不能为空");
            return map;
        }
        // 2.注册店铺
        if (shop != null && shopImg != null) {
            PersonInfo owner = (PersonInfo)httpServletRequest.getSession().getAttribute("user");
            shop.setPersonInfo(owner);
            /*// 创建一个空文件
            File shopImgFile = new File(PathUtil.getImgBasePath() + ImageUtil.getRandomFileName());
            try {
            	shopImgFile.createNewFile();
            } catch (IOException e) {
            	map.put("success", false);
            	map.put("errMsg", e.getMessage());
            	return map;
            }
            try {
            	inputStreamTofile(shopImg.getInputStream(), shopImgFile);
            } catch (IOException e) {
            	map.put("success", false);
            	map.put("errMsg", e.getMessage());
            	return map;
            }*/
            ShopExecution se;
            try {
                ImageHolder thuminal = new ImageHolder(shopImg.getOriginalFilename(), shopImg.getInputStream());
                se = shopService.addShop(shop, thuminal);
                if (se.getState() == ShopStateEnum.CHECK.getState()) {
                    map.put("success", true);
                    // 该用户可以操作的店铺列表
                    List<Shop> shopList = (List<Shop>)httpServletRequest.getSession().getAttribute("shopList");
                    if (shopList == null || shopList.size() == 0) {
                        shopList = new ArrayList<Shop>();
                    }
                    shopList.add(se.getShop());
                    httpServletRequest.getSession().setAttribute("shopList", shopList);
                } else {
                    map.put("success", false);
                    map.put("errMsg", se.getStateInfo());
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return map;
        } else {
            map.put("success", false);
            map.put("errMsg", "请输入店铺信息");
            return map;
        }
    }

    /**
     * 根据shopId获取商铺信息
     * 
     * @param request
     * @return
     */
    @GetMapping("/getshopbyid")
    @ResponseBody
    public Map<String, Object> getShopById(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        Long shopId = HttpRequestUtil.getLong(request, "shopId");
        if (shopId > -1) {
            try {
                Shop shop = shopService.getByShopId(shopId);
                List<Area> areaList = areaService.getAreaList();
                map.put("shop", shop);
                map.put("areaList", areaList);
                map.put("success", true);
                return map;
            } catch (Exception e) {
                map.put("success", false);
                map.put("errMsg", e.toString());
            }
        } else {
            map.put("success", false);
            map.put("errMsg", "empty shopId");
        }
        return map;
    }

    /**
     * 修改商铺信息
     * 
     * @param httpServletRequest
     * @param key
     * @return
     */
    @PostMapping("/modifyshop")
    @ResponseBody
    public Map<String, Object> modifyShop(HttpServletRequest httpServletRequest) {
        Map<String, Object> map = new HashedMap();
        if (!CodeUtil.checkVerifyCode(httpServletRequest)) {
            map.put("success", false);
            map.put("errMsg", "输入了错误的验证码");
            return map;
        }
        // 1.接收并转化相应的参数，包括店铺信息以及图片信息
        // 将表单提交的内容转化为string类型
        String shopStr = HttpRequestUtil.getString(httpServletRequest, "shopStr");
        ObjectMapper mapper = new ObjectMapper();
        Shop shop = null;
        try {
            // 通过jackson-databind将string类型，转为实体类
            shop = mapper.readValue(shopStr, Shop.class);
        } catch (Exception e) {
            map.put("success", false);
            map.put("errMsg", e.getMessage());
            return map;
        }
        // 获取文件
        CommonsMultipartFile shopImg = null;
        CommonsMultipartResolver commonsMultipartResolver =
            new CommonsMultipartResolver(httpServletRequest.getSession().getServletContext());
        if (commonsMultipartResolver.isMultipart(httpServletRequest)) {
            MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest)httpServletRequest;
            shopImg = (CommonsMultipartFile)multipartHttpServletRequest.getFile("shopImg");
        }
        // 2.更改店铺
        if (shop != null && shop.getShopId() != null) {
            ShopExecution se;
            try {
                if (shopImg == null) {
                    se = shopService.updateShop(shop, null);
                } else {
                    ImageHolder thuminal = new ImageHolder(shopImg.getOriginalFilename(), shopImg.getInputStream());
                    se = shopService.updateShop(shop, thuminal);
                }
                if (se.getState() == ShopStateEnum.SUCCESS.getState()) {
                    map.put("success", true);
                } else {
                    map.put("success", false);
                    map.put("errMsg", se.getStateInfo());
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return map;
        } else {
            map.put("success", false);
            map.put("errMsg", "请输入店铺Id");
            return map;
        }
    }

    /**
     * 将CommonsMultipartFile转为file
     * 
     * @param ins
     * @param file
     */
    /*private static void inputStreamTofile(InputStream ins, File file) {
    	OutputStream os = null;
    	try {
    		os = new FileOutputStream(file);
    		int bytesRead = 0;
    		byte[] buffer = new byte[1024];
    		while ((bytesRead = ins.read(buffer)) != -1) {
    			os.write(buffer, 0, bytesRead);
    		}
    	} catch (Exception e) {
    		throw new RuntimeException("调用inputStreamToFile产生异常" + e.getMessage());
    	} finally {
    		try {
    			if (os != null)
    				os.close();
    			if (ins != null)
    				ins.close();
    		} catch (IOException e) {
    			throw new RuntimeException("调用inputStreamToFile产生异常" + e.getMessage());
    		}
    
    	}
    }*/
}
