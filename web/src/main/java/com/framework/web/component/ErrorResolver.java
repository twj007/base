package com.framework.web.component;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.AuthorizationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/***
 **@project: base
 **@description:
 **@Author: twj
 **@Date: 2019/07/16
 **/
@RestControllerAdvice
public class ErrorResolver {

    private static final Logger logger = LoggerFactory.getLogger(ErrorResolver.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity exceptionhandler(Exception e){
        e.printStackTrace();
        return ResponseEntity.badRequest().body("系统异常");
    }


    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity authenticationException(AuthenticationException e){
        return ResponseEntity.badRequest().body("登陆失败");
    }

    @ExceptionHandler(AuthorizationException.class)
    public ResponseEntity authorizationException(AuthorizationException e){
        return ResponseEntity.badRequest().body("未授权或登陆超时"+e.getMessage());
    }

}
