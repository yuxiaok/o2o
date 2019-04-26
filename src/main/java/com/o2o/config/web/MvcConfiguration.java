package com.o2o.config.web;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

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
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // registry.addResourceHandler("/resources/**").addResourceLocations("classpath:/resources/");
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
     * 配置视图解析器
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
}
