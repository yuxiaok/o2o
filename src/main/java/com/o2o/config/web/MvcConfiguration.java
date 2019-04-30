package com.o2o.config.web;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.google.code.kaptcha.servlet.KaptchaServlet;
import com.o2o.interceptor.ShopLoginInterceptor;
import com.o2o.interceptor.ShopPermissionInterceptor;

/**
 * @author kai.yu
 * @date 2019/4/26 mvc配置
 **/
@Configuration
/**
 * 等价于<mvc:annotation-driven/>
 */
@EnableWebMvc
public class MvcConfiguration extends WebMvcConfigurerAdapter implements ApplicationContextAware {
    /**
     * spring容器
     */
    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    /**
     * 静态资源映射
     * 
     * @param registry
     */
    // TODO
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // registry.addResourceHandler("/resources/**").addResourceLocations("classpath:/resources/");
        registry.addResourceHandler("/upload/**").addResourceLocations("file:D:/projectdev/image/upload/");
    }

    /**
     * 默认处理器，等同于<mvc:default-servlet-handler/>
     * 
     * @param configurer
     */
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    /**
     * 添加拦截器
     * 
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        String interceptPath = "/shopadmin/**";
        // 添加拦截器
        InterceptorRegistration loginIr = registry.addInterceptor(new ShopLoginInterceptor());
        // 拦截路径
        loginIr.addPathPatterns(interceptPath);
        // 添加另一个拦截器
        InterceptorRegistration interceptorPermission = registry.addInterceptor(new ShopPermissionInterceptor());
        // 拦截路径
        interceptorPermission.addPathPatterns(interceptPath);
        // 不拦截的路径
        // shoplist page
        interceptorPermission.excludePathPatterns("/shopadmin/shoplist");
        interceptorPermission.excludePathPatterns("/shopadmin/getshoplist");
        // shopregiester page
        interceptorPermission.excludePathPatterns("/shopadmin/getshopinitinfo");
        interceptorPermission.excludePathPatterns("/shopadmin/registershop");
        interceptorPermission.excludePathPatterns("/shopadmin/shopoperation");
        // shopmange page
        interceptorPermission.excludePathPatterns("/shopadmin/shopmanagement");
        interceptorPermission.excludePathPatterns("/shopadmin/getshopmanagementinfo");
    }

    /**
     * 配置视图解析器
     * 
     * @return
     */
    @Bean(name = "viewResolver")
    public ViewResolver createViewResolver() {
        InternalResourceViewResolver internalResourceViewResolver = new InternalResourceViewResolver();
        // 设置spring容器
        internalResourceViewResolver.setApplicationContext(applicationContext);
        // 取消缓存
        internalResourceViewResolver.setCache(false);
        // 前缀
        internalResourceViewResolver.setPrefix("/WEB-INF/html/");
        // 后缀
        internalResourceViewResolver.setSuffix(".html");
        return internalResourceViewResolver;
    }

    /**
     * 配置文件上传解析器
     * 
     * @return
     */
    @Bean(name = "multipartResolver")
    public CommonsMultipartResolver createCommonsMultipartResolver() {
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
        commonsMultipartResolver.setDefaultEncoding("utf-8");
        // 1024*1024*20 = 20m
        commonsMultipartResolver.setMaxUploadSize(20971520);
        commonsMultipartResolver.setMaxInMemorySize(20971520);
        return commonsMultipartResolver;
    }

    /**
     * 配置验证码，注册一个servlet
     */
    @Value("${kaptcha.border}")
    private String border;
    @Value("${kaptcha.textproducer.font.color}")
    private String color;
    @Value("${kaptcha.image.width}")
    private String width;
    @Value("${kaptcha.textproducer.char.string}")
    private String charString;
    @Value("${kaptcha.image.height}")
    private String height;
    @Value("${kaptcha.textproducer.font.size}")
    private String fontSize;
    @Value("${kaptcha.noise.color}")
    private String noiseColor;
    @Value("${kaptcha.textproducer.char.length}")
    private String charLength;
    @Value("${kaptcha.textproducer.font.names}")
    private String frontNames;

    @Bean
    public ServletRegistrationBean servletRegistrationBean() {
        ServletRegistrationBean<KaptchaServlet> kaptchaServletServletRegistrationBean =
            new ServletRegistrationBean<>(new KaptchaServlet(), "/Kaptcha");
        // 边框
        kaptchaServletServletRegistrationBean.addInitParameter("kaptcha.border", border);
        // 字体颜色
        kaptchaServletServletRegistrationBean.addInitParameter("kaptcha.textproducer.font.color", color);
        // 图片宽度
        kaptchaServletServletRegistrationBean.addInitParameter("kaptcha.image.width", width);
        // 哪些字符生成验证码
        kaptchaServletServletRegistrationBean.addInitParameter("kaptcha.textproducer.char.string", charString);
        // 图片高度
        kaptchaServletServletRegistrationBean.addInitParameter("kaptcha.image.height", height);
        // 字体大小
        kaptchaServletServletRegistrationBean.addInitParameter("kaptcha.textproducer.font.size", fontSize);
        // 干扰线颜色
        kaptchaServletServletRegistrationBean.addInitParameter("kaptcha.noise.color", noiseColor);
        // 字符个数
        kaptchaServletServletRegistrationBean.addInitParameter("kaptcha.textproducer.char.length", charLength);
        // 字体
        kaptchaServletServletRegistrationBean.addInitParameter("kaptcha.textproducer.font.names", frontNames);
        return kaptchaServletServletRegistrationBean;
    }
}
