package com.quartz.proxy;

import org.quartz.Scheduler;
import org.quartz.SchedulerConfigException;
import org.quartz.core.JobRunShellFactory;
import org.quartz.core.QuartzScheduler;
import org.quartz.core.QuartzSchedulerResources;
import org.quartz.impl.StdScheduler;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/***
 **@project: base
 **@description:
 **@Author: twj
 **@Date: 2019/10/08
 **/
public class StdSchedulerFactoryProxy extends StdSchedulerFactory {

    private static final Logger logger = LoggerFactory.getLogger(StdSchedulerFactoryProxy.class);

    @Override
    protected Scheduler instantiate(QuartzSchedulerResources rsrcs, QuartzScheduler qs) {
        Scheduler scheduler = new StdScheduler(qs);
        try {
            JobRunShellFactory jobFactory=new StdJobRunnShellFactoryProxy();
            jobFactory.initialize(scheduler);
            rsrcs.setJobRunShellFactory(jobFactory);
        } catch (SchedulerConfigException e) {
            logger.error("初始化MonitorStdJobRunShellFactory出错",e);
        }
        return scheduler;
    }
}
