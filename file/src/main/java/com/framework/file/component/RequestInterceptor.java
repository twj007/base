package com.framework.file.component;

import com.alibaba.druid.support.json.JSONUtils;
import com.framework.file.util.RepeatScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/***
 **@project: base
 **@description: prevent repeat request by token
 **@Author: twj
 **@Date: 2019/07/10
 * 可以通过注解选择是否验证重复提交
 **/
@Component
public class RequestInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        Method method = ((HandlerMethod)handler).getMethod();
        Annotation annotation = method.getAnnotation(RepeatScan.class);
        if(annotation == null){
            return super.preHandle(request, response, handler);
        }else if(((RepeatScan) annotation).scan()){
            String token = request.getHeader("token");
            if(token != null){
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
                            stringBuffer.append(kv.getKey()).append("=");
                            for(String val : kv.getValue()){
                                stringBuffer.append(val).append(",");
                            }
                            stringBuffer.deleteCharAt(stringBuffer.length()-1);
                            stringBuffer.append("&");
                        }
                        requestURI = stringBuffer.toString().substring(0, stringBuffer.toString().length()-1);
                        break;
                    default:
                        requestURI = "";
                        break;
                }
                if(!"".equals(requestURI)) {
                    Object val = redisTemplate.opsForHash().get(token, requestURI);
                    if (val != null) {
                        response.setCharacterEncoding("UTF-8");
                        response.setContentType("application/json; charset=utf-8");
                        PrintWriter out = response.getWriter();
                        Map<String, String> res = new HashMap<>();
                        res.put("msg", "请勿重复提交");
                        res.put("code", "400");
                        String result = JSONUtils.toJSONString(res);
                        out.append(result);
                        return false;
                    } else {
                        redisTemplate.opsForHash().put(token, requestURI, 1);
                        redisTemplate.expire(token, 30, TimeUnit.SECONDS);
                    }
                }
            }
        }
        return super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
    }
}
