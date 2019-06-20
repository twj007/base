package com.mall.component;

import com.mall.util.EncryptUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/***
 **@project: base
 **@description: jwt filter
 **@Author: twj
 **@Date: 2019/06/20
 **/
@WebFilter(urlPatterns = "/**")
@Order(2)
public class JwtFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(JwtFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.info("【初始化jwt filter】");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        String token = ((HttpServletRequest)request).getHeader("token");
        if(EncryptUtils.verify(token)){
            filterChain.doFilter(request, response);
        }else{
            ((HttpServletResponse)response).sendRedirect("/error");
        }
    }

    @Override
    public void destroy() {

    }
}
