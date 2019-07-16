package com.shiro.component;

import org.apache.shiro.authz.AuthorizationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
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

    @ExceptionHandler(AuthorizationException.class)
    public ResponseEntity authorizationException(AuthorizationException e){
        return ResponseEntity.badRequest().body("未授权或未登陆"+e.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity unexpectedException(RuntimeException e){
        return ResponseEntity.badRequest().body("未知错误");
    }
}
