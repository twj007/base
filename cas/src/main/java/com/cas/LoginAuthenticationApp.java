package com.cas;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.calendar.HolidayCalendar;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/***
 **@project: base
 **@description:
 **@Author: twj
 **@Date: 2019/09/05
 **/
@SpringBootApplication
@EnableWebMvc
public class LoginAuthenticationApp {

    public static void main(String[] args) {
        SpringApplication.run(LoginAuthenticationApp.class, args);
    }

}
