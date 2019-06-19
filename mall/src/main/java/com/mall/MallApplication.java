package com.mall;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/***
 **@project: base
 **@description: 整个demo
 **@Author: twj
 **@Date: 2019/06/18
 **/
@SpringBootApplication
@MapperScan(basePackages = "com.mall.dao")
@EnableTransactionManagement
@EnableAspectJAutoProxy
public class MallApplication {

    public static void main(String[] args) {
        SpringApplication.run(MallApplication.class, args);
    }
}
