package com.mall.component;

import org.redisson.api.RBucket;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

/***
 **@project: base
 **@description: to prevent repeat request
 **@Author: twj
 **@Date: 2019/07/10
 * 防止重复提交
 **/
public class RequestFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(RequestFilter.class);

    @Autowired
    private RedissonClient redissonClient;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.info("【requetsFiler】init");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse)servletResponse;
        RMap<String, Integer> reqParams = redissonClient.getMap(request.getRequestedSessionId());
        String requestURI;
        switch(request.getMethod()){
            case "GET":
                requestURI = request.getQueryString();
                break;
            case "POST":
                Map<String, String[]> params = request.getParameterMap();
                StringBuffer stringBuffer = new StringBuffer();
                Iterator<Map.Entry<String, String[]>> it = params.entrySet().iterator();
                while (it.hasNext()){
                    Map.Entry<String, String[]> kv = it.next();
                    stringBuffer.append(kv.getKey()).append("=").append(kv.getValue());
                }
                requestURI = stringBuffer.toString();
                break;
            default:
                requestURI = "";
                break;
        }
        if(reqParams.get(requestURI) == null){
            reqParams.entrySet(requestURI, 1);
            filterChain.doFilter(servletRequest, servletResponse);
        }else{
            response.setContentType("application/json");
            response.sendError(400, "请勿重复提交");
        }
    }

    @Override
    public void destroy() {

    }
}
