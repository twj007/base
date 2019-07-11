//package com.mall.config;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.CacheControl;
//import org.springframework.util.ResourceUtils;
//import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
//
///***
// **@project: base
// **@description:
// **@Author: twj
// **@Date: 2019/06/19
// **/
//@Configuration
//public class StaticPathConfig extends WebMvcConfigurationSupport {
//    @Override
//    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
//        CacheControl cacheControl = CacheControl.empty();
//        registry.addResourceHandler("/static/**").addResourceLocations(ResourceUtils.CLASSPATH_URL_PREFIX+ "/static/");
//        //registry.addResourceHandler(new String[]{"/webjars/**"}).addResourceLocations(new String[]{"classpath:/META-INF/resources/webjars/"}).setCachePeriod(Math.toIntExact(Long.valueOf(7200))).setCacheControl(cacheControl);
//
//    }
//}
