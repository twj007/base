package com.mall.component;

import com.mall.util.EncryptUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/***
 **@project: base
 **@description: jwt filter
 **@Author: twj
 **@Date: 2019/06/20
 **/
@WebFilter(urlPatterns = "/**")
@Order(1)
@Configuration
public class JwtFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(JwtFilter.class);

    @Value("#{'${project.properties.white.tables}'.split(',')}")
    private List<String> whiteTables;


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

        logger.info("【初始化jwt filter】");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse resp = (HttpServletResponse)response;
        HttpServletRequest req = (HttpServletRequest)request;
        String uri = req.getRequestURI();
        if("/".equals(uri)){
            resp.sendRedirect("/index");
        }
        boolean flag = false;
        for(String url : whiteTables){
            if(uri.indexOf(url) != -1){
                flag = true;
                break;
            }
        }
        if(flag){
            filterChain.doFilter(request, response);
        }else{
            String token = ((HttpServletRequest)request).getHeader("token");
            if(EncryptUtils.verify(token)){
                filterChain.doFilter(request, response);
            }else{
                resp.setContentType("application/json");
                resp.sendError(500, "登陆失效，请重新登陆");
            }
        }
    }

    @Override
    public void destroy() {

    }
}
