package com.framework.web.component;

import org.apache.shiro.session.Session;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.springframework.beans.factory.annotation.Value;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/***
 **@project: base
 **@description:
 **@Author: twj
 **@Date: 2019/07/17
 * 对于token过期的要重新授权登陆
 **/
public class ForceLogoutFilter extends AccessControlFilter {


    @Value("${oauth2.client.id}")
    private String clientId;

    @Value("${oauth2.client.secret}")
    private String secret;



    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        //如果是token类型的认证的话就返回false， 其他类型的返回true
        if(true){
            return true;
        }else{
            return false;
        }
    }
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {



        return false;
    }
}
