package com.mall.config;

import com.mall.component.JWTInterceptor;
import com.mall.component.RequestInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.Arrays;
import java.util.List;

/***
 **@project: base
 **@description: to open swagger
 **@Author: twj
 **@Date: 2019/06/20
 **/
@Configuration
public class MvcConfig extends WebMvcConfigurationSupport {

    @Value("#{'project.exclude.path'.split(',')}")
    private List<String> excludePath;

    @Autowired
    private JWTInterceptor jwtInterceptor;

    @Autowired
    private RequestInterceptor requestInterceptor;


    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("static/**")
                .addResourceLocations("classpath:/resources/static/")
                .addResourceLocations("classpath:/META-INF/resources/static/")
                .addResourceLocations("classpath:/static/");
    }

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        if(excludePath == null || excludePath.size() == 0){
            registry.addInterceptor(jwtInterceptor).addPathPatterns("/**");
            registry.addInterceptor(requestInterceptor).addPathPatterns("/**");
        }else{
            registry.addInterceptor(jwtInterceptor).addPathPatterns("/**").excludePathPatterns(excludePath);
            registry.addInterceptor(requestInterceptor).addPathPatterns("/**").excludePathPatterns(excludePath);
        }
        super.addInterceptors(registry);
    }
}
