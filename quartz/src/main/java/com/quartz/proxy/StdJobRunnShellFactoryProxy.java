package com.quartz.proxy;

import org.quartz.Scheduler;
import org.quartz.SchedulerConfigException;
import org.quartz.SchedulerException;
import org.quartz.core.JobRunShell;
import org.quartz.core.JobRunShellFactory;
import org.quartz.impl.StdJobRunShellFactory;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.spi.TriggerFiredBundle;

/***
 **@project: base
 **@description:
 **@Author: twj
 **@Date: 2019/10/08
 **/
public class  StdJobRunnShellFactoryProxy implements JobRunShellFactory {

    private Scheduler scheduler;

    @Override
    public void initialize(Scheduler scheduler) throws SchedulerConfigException {
        this.scheduler = scheduler;
    }

    @Override
    public JobRunShell createJobRunShell(TriggerFiredBundle triggerFiredBundle) throws SchedulerException {
        return new MonitorRunShell(scheduler, triggerFiredBundle);
    }
}
