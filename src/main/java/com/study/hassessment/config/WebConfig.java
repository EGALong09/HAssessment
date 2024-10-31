package com.study.hassessment.config;

import com.study.hassessment.interceptors.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 配置类
 * configuration标识配置类
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private LoginInterceptor loginInterceptor;

        @Value("${accessFile.avatarHandler}")
//    @Value("${serviceFile.avatarHandler}")
    private String avatarHandler; //匹配url中的资源映射

        @Value("${accessFile.avatarLocation}")
//    @Value("${serviceFile.avatarLocation}")
    private String avatarLocation; //上传文件保存的本地目录

        @Value("${accessFile.headImgHandler}")
//    @Value("${serviceFile.headImgHandler}")
    private String headImgHandler; //匹配url 中的资源映射

        @Value("${accessFile.headImgLocation}")
//    @Value("${serviceFile.headImgLocation}")
    private String headImgLocation; //上传文件保存的本地目录

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //注册拦截器
        //登录接口和注册接口不拦截
        registry.addInterceptor(loginInterceptor).excludePathPatterns("/user/login", "/user/register", "/static/**");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //匹配到resourceHandler,将URL映射至location,也就是本地文件夹
        registry.addResourceHandler(avatarHandler).addResourceLocations("file:" + avatarLocation);
        registry.addResourceHandler(headImgHandler).addResourceLocations("file:" + headImgLocation);
    }
}
