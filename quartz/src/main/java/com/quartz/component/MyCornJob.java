package com.quartz.component;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

/***
 **@project: base
 **@description:
 **@Author: twj
 **@Date: 2019/09/23
 **/
public class MyCornJob  extends QuartzJobBean {
    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("this is a corn job");
        System.out.println("end corn expression job");
    }
}
