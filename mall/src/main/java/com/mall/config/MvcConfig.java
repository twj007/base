package com.mall.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/***
 **@project: base
 **@description: to open swagger
 **@Author: twj
 **@Date: 2019/06/20
 **/
@Configuration
public class MvcConfig extends WebMvcConfigurationSupport {

    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("static/**")
                .addResourceLocations("classpath:/resources/static/")
                .addResourceLocations("classpath:/META-INF/resources/static/")
                .addResourceLocations("classpath:/static/");
    }
}
