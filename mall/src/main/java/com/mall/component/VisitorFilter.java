package com.mall.component;

import com.mall.dao.UmsMemberMapper;
import com.mall.model.RequestRecord;
import com.mall.util.EncryptUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Base64;

/***
 **@project: base
 **@description: to control visitor
 **@Author: twj
 **@Date: 2019/06/21
 **/
@WebFilter(urlPatterns = "/**")
@Order(3)
public class VisitorFilter implements Filter {


    @Autowired
    private UmsMemberMapper umsMemberMapper;

    private static final Logger logger = LoggerFactory.getLogger(VisitorFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.info("【初始化】 visitor filter init");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        RequestRecord record = new RequestRecord();
        record.setAddress(request.getRemoteAddr());
        record.setHost(request.getRemoteHost());
        record.setPort(request.getRemotePort());
        record.setUri(request.getRequestURI());
        record.setQueryString(request.getQueryString());
        String token = request.getHeader("token");
        // 记录请求操作记录， 最好不存数据库
        // umsMemberMapper.saveRequestRecord(record);
        record.setVisitor(EncryptUtils.getIssuer(token));
        filterChain.doFilter(servletRequest, servletResponse);

    }

    @Override
    public void destroy() {

    }
}
