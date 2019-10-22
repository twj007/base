package com.quartz.proxy;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.core.JobRunShell;
import org.quartz.spi.TriggerFiredBundle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/***
 **@project: base
 **@description:
 **@Author: twj
 **@Date: 2019/10/08
 **/
public class MonitorRunShell extends JobRunShell {
    private static final Logger logger = LoggerFactory.getLogger(MonitorRunShell.class);

    public MonitorRunShell(Scheduler scheduler, TriggerFiredBundle bndle) {
        super(scheduler, bndle);
    }

    @Override
    protected void begin() throws SchedulerException {
        super.begin();
        try {
            JobDetail jobDetail = jec.getJobDetail();
            logger.info("【执行job begin】:job: {}, time: {}", jobDetail.getKey().getName(), jec.getJobRunTime());
        } catch (Exception e) {
            logger.error("记录job开始时间异常",e);
        }catch (Throwable e) {
            logger.error("记录job开始时间出错",e);
        }

    }

    @Override
    protected void complete(boolean successfulExecution) throws SchedulerException {
        super.complete(successfulExecution);
        try {
            logger.info("【job执行结束】: job: {}, time: {}", jec.getJobDetail().getKey(), jec.getJobRunTime());
        } catch (Exception e) {
            logger.error("记录job结束时间异常",e);
        }catch (Throwable e) {
            logger.error("记录job结束时间出错",e);
        }

    }
}
