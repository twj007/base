//package com.framework.file.component;
//
//import com.alibaba.druid.support.json.JSONUtils;
//import com.fasterxml.jackson.annotation.JsonCreator;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.core.annotation.Order;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.http.HttpMethod;
//
//import javax.servlet.*;
//import javax.servlet.annotation.WebFilter;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.Map;
//import java.util.concurrent.TimeUnit;
//
///***
// **@project: base
// **@description: to prevent repeat request
// **@Author: twj
// **@Date: 2019/07/10
// * 防止重复提交 缓存请求的方式
// **/
//@WebFilter(urlPatterns = "/*")
//@Order(3)
//public class RequestFilter implements Filter {
//
//    private static final Logger logger = LoggerFactory.getLogger(RequestFilter.class);
//
//    @Autowired
//    private RedisTemplate redis;
//
//    @Override
//    public void init(FilterConfig filterConfig) throws ServletException {
//        logger.info("【requetsFiler】init redis: {}", redis.toString());
//    }
//
//    @Override
//    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//        HttpServletRequest request = (HttpServletRequest)servletRequest;
//        HttpServletResponse response = (HttpServletResponse)servletResponse;
//        String requestURI;
//        switch(request.getMethod()){
//            case "GET":
//                requestURI = request.getQueryString();
//                break;
//            case "POST":
//                Map<String, String[]> params = request.getParameterMap();
//                StringBuffer stringBuffer = new StringBuffer();
//                Iterator<Map.Entry<String, String[]>> it = params.entrySet().iterator();
//                while (it.hasNext()){
//                    Map.Entry<String, String[]> kv = it.next();
//                    stringBuffer.append(kv.getKey()).append("=").append(kv.getValue());
//                }
//                requestURI = stringBuffer.toString();
//                break;
//            default:
//                requestURI = "";
//                break;
//        }
//        if(requestURI == ""){
//            filterChain.doFilter(servletRequest, servletResponse);
//        }
//        if(redis.opsForHash().get(requestURI, requestURI) == null){
//            redis.opsForHash().put(requestURI, requestURI, 1);
//            redis.expire(requestURI, 30, TimeUnit.SECONDS);
//            filterChain.doFilter(servletRequest, servletResponse);
//        }else{
//            response.setCharacterEncoding("UTF-8");
//            response.setContentType("application/json; charset=utf-8");
//            PrintWriter out = response.getWriter();
//            Map<String, String> res = new HashMap<>();
//            res.put("msg", "请勿重复提交");
//            res.put("code", "400");
//            String result = JSONUtils.toJSONString(res);
//            out.append(result);
//        }
//    }
//
//    @Override
//    public void destroy() {
//
//    }
//}
//
