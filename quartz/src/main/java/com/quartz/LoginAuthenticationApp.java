package com.quartz;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/***
 **@project: base
 **@description:
 **@Author: twj
 **@Date: 2019/09/05
 **/
@SpringBootApplication
@EnableWebMvc
@MapperScan(basePackages = {"com.quartz.dao"})
public class LoginAuthenticationApp {

    public static void main(String[] args) {
        SpringApplication.run(LoginAuthenticationApp.class, args);
    }

}
