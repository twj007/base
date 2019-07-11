package com.framework.file.config;

import com.framework.file.component.TokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.util.ResourceUtils;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.time.Duration;

/***
 **@project: base
 **@description:
 **@Author: twj
 **@Date: 2019/03/28
 **/
@Configuration
public class StaticConfig extends WebMvcConfigurationSupport {

    @Autowired
    private TokenFilter tokenFilter;

    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        CacheControl cacheControl = CacheControl.empty();
        registry.addResourceHandler("/static/**").addResourceLocations(ResourceUtils.CLASSPATH_URL_PREFIX+"/static/");
        registry.addResourceHandler(new String[]{"/webjars/**"}).addResourceLocations(new String[]{"classpath:/META-INF/resources/webjars/"}).setCachePeriod(Math.toIntExact(Long.valueOf(7200))).setCacheControl(cacheControl);
        super.addResourceHandlers(registry);

    }

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(tokenFilter).addPathPatterns("/**");
        super.addInterceptors(registry);
    }
}
