package com.quartz.configuration.mybatis;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInterceptor;
import org.apache.ibatis.logging.stdout.StdOutImpl;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/***
 **@project: base
 **@description:
 **@Author: twj
 **@Date: 2019/09/27
 **/
@Configuration
public class MybatisConfiguration {

    @Bean
    public PageHelper pageHelper() {
        PageHelper pageHelper = new PageHelper();
        Properties p = new Properties();
        p.setProperty("dialect", "Mysql");
        p.setProperty("offsetAsPageNum", "true");
        p.setProperty("rowBoundsWithCount", "true");
        pageHelper.setProperties(p);
        return pageHelper;
    }

    @Bean
    public ConfigurationCustomizer configurationCustomizer(){
        ConfigurationCustomizer configurationCustomizer = new ConfigurationCustomizer() {
            @Override
            public void customize(org.apache.ibatis.session.Configuration configuration) {
                configuration.setMapUnderscoreToCamelCase(true);
                configuration.setLogImpl(StdOutImpl.class);
                configuration.addInterceptor(new PageInterceptor());
            }
        };
        return configurationCustomizer;
    }
}
