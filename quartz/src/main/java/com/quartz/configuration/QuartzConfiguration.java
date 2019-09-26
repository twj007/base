package com.quartz.configuration;

import com.mysql.jdbc.Blob;
import com.quartz.component.MyCornJob;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.EverythingMatcher;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.util.ResourceUtils;

import javax.sql.DataSource;
import java.io.FileNotFoundException;

/***
 **@project: base
 **@description:
 **@Author: twj
 **@Date: 2019/09/23
 **/
@Configuration
public class QuartzConfiguration {


    @Bean
    Scheduler scheduler(SchedulerFactoryBean factory, @Qualifier("cronJob")JobDetail cronJob) throws SchedulerException {

        Scheduler scheduler = factory.getScheduler();
        scheduler.addJob(cronJob, true);
        return scheduler;
    }


    // 使用jobDetail包装job
//    @Bean
//    public JobDetail myJobDetail() {
//        return JobBuilder
//                .newJob(MyJob.class)
//                .withIdentity("myJob")
//                .storeDurably()
//                .withDescription("[it's a test quartz job]")
//                .build();
//    }
//
//    // 把jobDetail注册到trigger上去
//    @Bean
//    public Trigger myJobTrigger() {
//        SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder
//                .simpleSchedule()
//                .withIntervalInSeconds(15)
//                .repeatForever();
//
//        return TriggerBuilder.newTrigger()
//                .forJob(myJobDetail())
//                .withIdentity("myJobTrigger")
//                .withSchedule(scheduleBuilder)
//                .modifiedByCalendar("holidayCal")
//                .build();
//    }

//    @Bean("holidayCal")
//    HolidayCalendar holidayCalendar() {
//
//        HolidayCalendar holidayCalendar = new HolidayCalendar();
//        holidayCalendar.addExcludedDate(new Date());
//        return holidayCalendar;
//    }

    // 使用jobDetail包装job
    @Bean("cronJob")
    public JobDetail myCronJobDetail() {
        return JobBuilder.newJob(MyCornJob.class)
                .withIdentity("myCornJob")
                .storeDurably()
                .withDescription("[it's a job using cron expression]")
                .build();
    }

    // 把jobDetail注册到Cron表达式的trigger上去
    @Bean
    public Trigger CronJobTrigger() {
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule("0/10 * * * * ?");

        return TriggerBuilder.newTrigger()
                .forJob(myCronJobDetail())
                .withIdentity("myCornJob")
                .withSchedule(cronScheduleBuilder)
    //            .modifiedByCalendar("normalCal")
                .build();
    }

//    @Bean("normalCal")
//    public CronCalendar calendar() throws ParseException {
//        CronCalendar calendar = new CronCalendar("10 * 15 * * ?");
//        return calendar;
//    }
}

