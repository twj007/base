package com.framework.web.config;

import groovy.lang.Binding;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/***
 **@project: base
 **@description:
 **@Author: twj
 **@Date: 2019/10/09
 **/
@Configuration
class GroovyBindingConfiguration implements ApplicationContextAware {

    private ApplicationContext applicationContext

    @Bean("groovyBinding")
    Binding groovyBinding() {
        Binding groovyBinding = new Binding()

        Map<String, Object> beanMap = applicationContext.getBeansOfType(Object.class)
        //遍历设置所有bean,可以根据需求在循环中对bean做过滤
        for (String beanName : beanMap.keySet()) {
            groovyBinding.setVariable(beanName, beanMap.get(beanName))
        }
        return groovyBinding
    }

    @Override
    void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext
    }


}
