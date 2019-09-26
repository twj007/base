package com.quartz.component;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.time.LocalDateTime;

/***
 **@project: base
 **@description:
 **@Author: twj
 **@Date: 2019/09/23
 **/
public class MyJob extends QuartzJobBean {

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("start My Job：" + LocalDateTime.now());
        System.out.println("end  My Job：" + LocalDateTime.now());

    }
}
