package com.o2o.controller.local;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.o2o.dto.LocalAuthExecution;
import com.o2o.entity.LocalAuth;
import com.o2o.entity.PersonInfo;
import com.o2o.enums.LocalAuthStateEnum;
import com.o2o.service.LocalAuthService;
import com.o2o.util.CodeUtil;
import com.o2o.util.HttpRequestUtil;
import com.o2o.util.MD5;

/**
 * Created by yukai 2019/4/14 15:16
 */
@Controller
@RequestMapping("/local")
public class LocalAuthController {
    @Autowired
    private LocalAuthService localAuthService;

    /**
     * 将用户信息与平台账号绑定
     * 
     * @param request
     * @return
     */
    @PostMapping("/bindlocalauth")
    @ResponseBody
    public Map<String, Object> bindLocalAuth(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        // 验证码校验
        if (!CodeUtil.checkVerifyCode(request)) {
            map.put("success", false);
            map.put("errMsg", "验证码错误");
            return map;
        }

        // 获取输入的账号
        String userName = HttpRequestUtil.getString(request, "userName");
        // 获取输入的密码
        String password = HttpRequestUtil.getString(request, "password");
        // 从session中获取用户信息（用户一旦通过微信登录后，便能获取到用户的信息）
        PersonInfo user = (PersonInfo)request.getSession().getAttribute("user");
        // 非空判断，要求账号密码以及当前的用户session非空
        if (null == userName || null == password || null == user || null == user.getUserId()) {
            map.put("success", false);
            map.put("errMsg", "用户名和密码均不能为空");
            return map;
        }

        // 创建LocalAuth对象并赋值
        LocalAuth localAuth = new LocalAuth();
        localAuth.setUserName(userName);
        localAuth.setPassword(password);
        localAuth.setPersonInfo(user);
        // 绑定账号
        LocalAuthExecution localAuthExecution = localAuthService.bingLocalAuth(localAuth);
        if (LocalAuthStateEnum.SUCCESS.getState() == localAuthExecution.getState()) {
            map.put("success", true);
        } else {
            map.put("success", false);
            map.put("errMsg", localAuthExecution.getStateInfo());
        }
        return map;
    }

    /**
     * 修改密码
     * 
     * @param request
     * @return
     */
    @PostMapping("/changelocalpwd")
    @ResponseBody
    public Map<String, Object> changeLocalPwd(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        // 验证码校验
        if (!CodeUtil.checkVerifyCode(request)) {
            map.put("success", false);
            map.put("errMsg", "验证码错误");
            return map;
        }

        // 获取账号
        String userName = HttpRequestUtil.getString(request, "userName");
        // 获取原密码
        String password = HttpRequestUtil.getString(request, "password");
        // 获取新密码
        String newPassword = HttpRequestUtil.getString(request, "newPassword");
        // 从session中获取用户信息
        PersonInfo user = (PersonInfo)request.getSession().getAttribute("user");
        if (null == userName || null == password || null == newPassword || null == user || null == user.getUserId()) {
            map.put("success", false);
            map.put("errMsg", "请输入密码");
            return map;
        }

        if (password.equals(newPassword)) {
            map.put("success", false);
            map.put("errMsg", "新密码与原密码相同，请重新输入");
            return map;
        }

        try {
            // 查看原先账号，看看与输入的账号是否一致，不一致则认为是非法操作
            LocalAuth localAuth = localAuthService.getLocalAuthByUserId(user.getUserId());
            if (null == localAuth || !localAuth.getUserName().equals(userName)) {
                // 不一致则直接退出
                map.put("success", false);
                map.put("errMsg", "输入的账号非本次登录的账号");
                return map;
            }

            if (!localAuth.getPassword().equals(MD5.getMd5(password))) {
                // 不一致则直接退出
                map.put("success", false);
                map.put("errMsg", "原密码错误");
                return map;
            }

            // 修改平台账号的密码
            LocalAuthExecution localAuthExecution =
                localAuthService.modifyLocalAuth(user.getUserId(), userName, password, newPassword);
            if (LocalAuthStateEnum.SUCCESS.getState() == localAuthExecution.getState()) {
                map.put("success", true);
            } else {
                map.put("success", false);
                map.put("errMsg", localAuthExecution.getStateInfo());
            }
        } catch (Exception e) {
            map.put("success", false);
            map.put("errMsg", e.toString());
            return map;
        }
        return map;
    }

    /**
     * 登录
     * 
     * @param request
     * @return
     */
    @PostMapping("/logincheck")
    @ResponseBody
    public Map<String, Object> loginCheck(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        // 获取是否需要进行验证码校验的标识符
        boolean needVerify = HttpRequestUtil.getBoolean(request, "needVerify");
        if (needVerify && !CodeUtil.checkVerifyCode(request)) {
            map.put("success", false);
            map.put("errMsg", "输入了错误的验证码");
            return map;
        }

        // 获取输入的账号
        String userName = HttpRequestUtil.getString(request, "userName");
        String password = HttpRequestUtil.getString(request, "password");
        // 非空校验
        if (null == userName || null == password) {
            map.put("success", false);
            map.put("errMsg", "用户名和密码均不能为空");
            return map;
        }

        // 根据账号和密码获取平台账号信息
        LocalAuth localAuth = localAuthService.getLocalAuthBuUsernameAndPwd(userName, password);
        if (null != localAuth) {
            map.put("success", true);
            request.getSession().setAttribute("user", localAuth.getPersonInfo());
        } else {
            map.put("success", false);
            map.put("errMsg", "用户名或密码错误");
        }
        return map;
    }

    /**
     * 当用户点击登出按钮时，注销session
     * 
     * @param request
     * @return
     */
    @PostMapping("/logout")
    @ResponseBody
    public Map<String, Object> logOut(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        // 将用户session置为空
        request.getSession().setAttribute("user", null);
        map.put("success", true);
        return map;
    }
}
