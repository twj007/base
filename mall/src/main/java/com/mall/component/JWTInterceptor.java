package com.mall.component;

import com.alibaba.druid.support.json.JSONUtils;
import com.mall.util.EncryptUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/***
 **@project: base
 **@description: jwt interceptor
 **@Author: twj
 **@Date: 2019/07/12
 **/
@Component
public class JWTInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return true;
//        if(request.getRequestURI().indexOf("test") != -1){
//            return true;
//        }
//        String token = request.getHeader("token");
//        if(EncryptUtils.verify(token)){
//            return super.preHandle(request, response, handler);
//        }else{
//            response.setCharacterEncoding("UTF-8");
//            response.setContentType("application/json; charset=utf-8");
//            PrintWriter out = response.getWriter();
//            Map<String, String> res = new HashMap<>();
//            res.put("msg", "验证失败，请重新登陆");
//            res.put("code", "500");
//            String result = JSONUtils.toJSONString(res);
//            out.append(result);
//            return false;
//        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
    }
}
