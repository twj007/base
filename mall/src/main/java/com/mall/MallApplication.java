package com.mall;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/***
 **@project: base
 **@description: 整个demo
 **@Author: twj
 **@Date: 2019/06/18
 **/
@SpringBootApplication
@MapperScan(basePackages = "com.mall.dao")
public class MallApplication {

    public static void main(String[] args) {
        SpringApplication.run(MallApplication.class, args);
    }
}
