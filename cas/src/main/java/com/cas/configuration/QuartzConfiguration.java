package com.cas.configuration;

import com.cas.component.MyCornJob;
import com.cas.component.MyJob;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.calendar.CronCalendar;
import org.quartz.impl.calendar.HolidayCalendar;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.datetime.DateFormatter;

import java.text.ParseException;
import java.util.Date;

/***
 **@project: base
 **@description:
 **@Author: twj
 **@Date: 2019/09/23
 **/
@Configuration
public class QuartzConfiguration {



    @Bean
    SchedulerFactory schedulerFactory(){
        return new StdSchedulerFactory();
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
    @Bean
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
                .withIdentity("myCronJobTrigger")
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

