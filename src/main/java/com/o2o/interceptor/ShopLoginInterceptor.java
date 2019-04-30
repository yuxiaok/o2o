package com.o2o.interceptor;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.o2o.entity.PersonInfo;

/**
 * @author kai.yu
 * @date 2019/4/19 店家管理系统登录验证拦截器，判断是否已登录
 **/
public class ShopLoginInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
        throws Exception {
        // 从session中取用户信息

        Object user = request.getSession().getAttribute("user");
        if (null != user) {
            // 若用户不为空，则将session里的用户信息转换成PersonInfo实体类对象
            PersonInfo personInfo = (PersonInfo)user;
            // 做空值判断，确保userId不为空并且该账号的可用状态为1,并且用户类型为店家
            if (personInfo != null && personInfo.getUserId() != null && personInfo.getUserId() > 0
                && personInfo.getEnableStatus() == 1) {
                // 若验证通过，则返回true,拦截器返回true之后，用户接下来的操作得以正常执行
                return true;
            }
        }
        // 若不满足登录验证，则直接跳转到登录页面
        PrintWriter writer = response.getWriter();
        writer.println("<html>");
        writer.println("<script>");
        writer.println("window.open('" + request.getContextPath() + "/local/login?usertype=2','_self')");
        writer.println("</script>");
        writer.println("</html>");
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
