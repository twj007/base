package com.app.component;

import org.jasig.cas.client.authentication.UrlPatternMatcherStrategy;

import java.util.Arrays;
import java.util.List;

/***
 **@project: base
 **@description:
 **@Author: twj
 **@Date: 2019/09/06
 **/
public class SimpleUrlPatternMatcherStrategy implements UrlPatternMatcherStrategy {

    /**
     * 机能概要: 判断是否匹配这个字符串
     *
     * @param url 用户请求的连接
     * @return true : 不拦截
     * false :必须得登录了
     */
    @Override
    public boolean matches(String url) {

        if(url.contains("/logout")){
            return true;
        }

        List<String> list = Arrays.asList(
                "/",
                "/index",
                "/favicon.ico"
        );

        String name = url.substring(url.lastIndexOf("/"));
        if (name.indexOf("?") != -1) {
            name = name.substring(0, name.indexOf("?"));
        }

        System.out.println("name：" + name);
        boolean result = list.contains(name);
        if (!result) {
            System.out.println("拦截URL：" + url);
        }
        return result;
    }

    /**
     * 正则表达式的规则，这个地方可以是web传递过来的
     */
    @Override
    public void setPattern(String pattern) {

    }
}
