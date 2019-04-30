package com.o2o.interceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.o2o.entity.Shop;

/**
 * @author kai.yu
 * @date 2019/4/19 验证用户是否有操作权限
 **/
public class ShopPermissionInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
        throws Exception {
        // 从session中获取当前选择的店铺
        Shop currendShop = (Shop)request.getSession().getAttribute("currentShop");
        // 从session中获取当前用户可操作的店铺列表
        List<Shop> shopList = (List<Shop>)request.getSession().getAttribute("shopList");
        // 非空判断
        if (currendShop != null && shopList != null) {
            // 判断当前用户是否对这个店铺有操作权限
            for (Shop shop : shopList) {
                if (shop.getShopId() == currendShop.getShopId()) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
        ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
        throws Exception {
        super.afterCompletion(request, response, handler, ex);
    }
}
