package com.o2o.controller.local;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by yukai 2019/4/14 18:58
 */
@Controller
@RequestMapping("/local")
public class LocalController {

    /**
     * 绑定账号页面路由
     * 
     * @return
     */
    @GetMapping("/accountbind")
    private String accountBind() {
        return "local/accountbind";
    }

    /**
     * 登录页路径
     * 
     * @return
     */
    @GetMapping("/login")
    private String login() {
        return "local/login";
    }

    /**
     * 修改密码
     * 
     * @return
     */
    @GetMapping("/changepsw")
    private String changepsw() {
        return "local/changepsw";
    }
}
