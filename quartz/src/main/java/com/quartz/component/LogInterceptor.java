package com.quartz.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/***
 **@project: base
 **@description:
 **@Author: twj
 **@Date: 2019/09/29
 **/
@Component
public class LogInterceptor extends HandlerInterceptorAdapter {

    private static final Logger logger = LoggerFactory.getLogger(LogInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logger.info("【quartz】 请求url：{}， 请求参数：{}， 请求ip：{}", request.getRequestURI(), request.getQueryString(), request.getRemoteAddr());
        return super.preHandle(request, response, handler);
    }
}
