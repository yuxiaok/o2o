package com.o2o.controller.superadmin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.o2o.dto.AwardExecution;
import com.o2o.dto.ImageHolder;
import com.o2o.entity.Award;
import com.o2o.entity.Shop;
import com.o2o.enums.AwardStateEnum;
import com.o2o.service.AwardService;
import com.o2o.util.CodeUtil;
import com.o2o.util.HttpRequestUtil;

@Controller
@RequestMapping("/shopadmin")
public class AwardManagementController {
    @Autowired
    private AwardService awardService;

    @RequestMapping(value = "/listawardsbyshop", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> listAwardsByShop(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        int pageIndex = HttpRequestUtil.getInt(request, "pageIndex");
        int pageSize = HttpRequestUtil.getInt(request, "pageSize");
        Shop currentShop = (Shop)request.getSession().getAttribute("currentShop");
        if ((pageIndex > -1) && (pageSize > -1) && (currentShop != null) && (currentShop.getShopId() != null)) {
            String awardName = HttpRequestUtil.getString(request, "awardName");
            Award awardCondition = compactAwardCondition4Search(currentShop.getShopId(), awardName);
            AwardExecution ae = awardService.getAwardList(awardCondition, pageIndex, pageSize);
            modelMap.put("awardList", ae.getAwardList());
            modelMap.put("count", ae.getCount());
            modelMap.put("success", true);
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "empty pageSize or pageIndex or shopId");
        }
        return modelMap;
    }

    @RequestMapping(value = "/getawardbyid", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> getAwardbyId(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        long awardId = HttpRequestUtil.getLong(request, "awardId");
        if (awardId > -1) {
            Award award = awardService.getAwardById(awardId);
            modelMap.put("award", award);
            modelMap.put("success", true);
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "empty awardId");
        }
        return modelMap;
    }

    @RequestMapping(value = "/addaward", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> addAward(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        if (!CodeUtil.checkVerifyCode(request)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "输入了错误的验证码");
            return modelMap;
        }
        ObjectMapper mapper = new ObjectMapper();
        Award award = null;
        ImageHolder thumbnail = null;
        String awardStr = HttpRequestUtil.getString(request, "awardStr");

        try {
            CommonsMultipartResolver multipartResolver =
                new CommonsMultipartResolver(request.getSession().getServletContext());
            if (multipartResolver.isMultipart(request)) {
                thumbnail = handleImage(request, thumbnail);
            }
        } catch (IOException e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.toString());
            return modelMap;
        }

        try {
            award = mapper.readValue(awardStr, Award.class);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.toString());
            return modelMap;
        }
        if (award != null && thumbnail != null) {
            try {
                Shop currentShop = (Shop)request.getSession().getAttribute("currentShop");
                award.setShopId(currentShop.getShopId());
                AwardExecution ae = awardService.addAward(award, thumbnail);
                if (ae.getState() == AwardStateEnum.SUCCESS.getState()) {
                    modelMap.put("success", true);
                } else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", ae.getStateInfo());
                }
            } catch (RuntimeException e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.toString());
                return modelMap;
            }

        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "请输入商品信息");
        }
        return modelMap;
    }

    @RequestMapping(value = "/modifyaward", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> modifyAward(HttpServletRequest request) {
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

        ObjectMapper mapper = new ObjectMapper();
        Award award = null;
        ImageHolder thumbnail = null;
        List<ImageHolder> productImgList = new ArrayList<>();

        // 文件上传视图解析
        CommonsMultipartResolver multipartResolver =
            new CommonsMultipartResolver(request.getSession().getServletContext());
        try {
            if (multipartResolver.isMultipart(request)) {
                thumbnail = handleImage(request, thumbnail);
            }
        } catch (Exception e) {
            map.put("success", false);
            map.put("errMsg", e.toString());
            return map;
        }

        try {
            // 获取前台传过来的product并转化为对象
            String awardStr = HttpRequestUtil.getString(request, "awardStr");
            award = mapper.readValue(awardStr, Award.class);
        } catch (IOException e) {
            map.put("success", false);
            map.put("errMsg", e.toString());
            return map;
        }

        // 空值判断
        if (award != null) {
            try {
                // 从session中获取店铺id，并赋值给product,减少对前端数据的依赖。
                Shop currentShop = (Shop)request.getSession().getAttribute("currentShop");
                award.setShopId(currentShop.getShopId());

                // 进行修改
                AwardExecution pe = awardService.modifyAward(award, thumbnail);
                if (pe.getState() == AwardStateEnum.SUCCESS.getState()) {
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

    private Award compactAwardCondition4Search(long shopId, String awardName) {
        Award awardCondition = new Award();
        awardCondition.setShopId(shopId);
        if (awardName != null) {
            awardCondition.setAwardName(awardName);
        }
        return awardCondition;
    }

    private ImageHolder handleImage(HttpServletRequest request, ImageHolder imageHolder) throws IOException {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)request;
        CommonsMultipartFile thumbnailFile = (CommonsMultipartFile)multipartRequest.getFile("thumbnail");
        imageHolder = new ImageHolder(thumbnailFile.getOriginalFilename(), thumbnailFile.getInputStream());
        return imageHolder;
    }

}
