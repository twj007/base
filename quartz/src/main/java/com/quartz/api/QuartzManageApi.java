package com.quartz.api;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.quartz.component.MyJob;
import com.quartz.dto.ScheduleJob;
import org.quartz.*;
import org.quartz.impl.JobDetailImpl;
import org.quartz.impl.QuartzServer;
import org.quartz.impl.calendar.CronCalendar;
import org.quartz.impl.matchers.GroupMatcher;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/***
 **@project: base
 **@description:
 **@Author: twj
 **@Date: 2019/09/26
 **/
@RestController
public class QuartzManageApi {


    @Autowired
    private Scheduler scheduler;


    @RequestMapping("/list/page")
    public ModelAndView listPage(ModelAndView modelAndView){
        modelAndView.setViewName("/list");
        return modelAndView;
    }

    @RequestMapping("/list")
    public ResponseEntity list() throws SchedulerException {
        List<ScheduleJob> jobs = new ArrayList<>();
        for (String groupName : scheduler.getJobGroupNames()) {
            for (JobKey jobKey : scheduler.getJobKeys(GroupMatcher.jobGroupEquals(groupName))) {
                JobDetail detail = scheduler.getJobDetail(jobKey);
                ScheduleJob job = new ScheduleJob();
                BeanUtils.copyProperties(detail, job);
                //get job's trigger
                Trigger trigger = scheduler.getTrigger(TriggerKey.triggerKey(jobKey.getName()));
                Trigger.TriggerState state = scheduler.getTriggerState(trigger.getKey());
                job.setState(state.name());
                Date nextFireTime = trigger.getNextFireTime();
                job.setCronExpression(((CronTriggerImpl)trigger).getCronExpression());
                job.setNextExecuteTime(nextFireTime);
                jobs.add(job);
            }
        }
        return ResponseEntity.ok(jobs);
    }

    @RequestMapping("/status")
    public ResponseEntity getSchedulerStatus()throws SchedulerException{

        return ResponseEntity.ok(scheduler.isStarted());
    }

    @RequestMapping("/shutdown")
    public ResponseEntity shutdown(){
        try {
            if(scheduler.isShutdown()){
                return ResponseEntity.ok("系统异常，调度器已关闭");
            }else{
                scheduler.standby();// 不能使用shutdown方法，不然关闭之后将无法再被启动
            }
        } catch (SchedulerException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("系统异常");
        }
        return ResponseEntity.ok("调度器关闭成功");
    }

    @RequestMapping("/resume")
    public ResponseEntity resume(){
        try {
//            if(scheduler.isStarted()){
//                return ResponseEntity.ok("调度器已启动");
//            }else{
                scheduler.start();
//            }
        } catch (SchedulerException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("系统异常");
        }
        return ResponseEntity.ok("启动成功");
    }

    @RequestMapping("/job/{jobKey}")
    public ResponseEntity getJobKey(@PathVariable("jobKey")String jobKey) throws SchedulerException {

        JobDetailImpl jobDetail = (JobDetailImpl) scheduler.getJobDetail(JobKey.jobKey(jobKey));
        Trigger trigger = scheduler.getTrigger(TriggerKey.triggerKey(jobKey));
        String cron = ((CronTriggerImpl)trigger).getCronExpression();
        ScheduleJob scheduleJob = new ScheduleJob();
        scheduleJob.setCronExpression(cron);
        BeanUtils.copyProperties(jobDetail, scheduleJob);
        return ResponseEntity.ok(scheduleJob);
    }

    @RequestMapping("/job/pause/{jobKey}")
    public ResponseEntity pauseJob(@PathVariable("jobKey")String jobKey){
        try {
            switch (jobKey){
                case "ALL": scheduler.pauseAll(); break;
                default:    scheduler.pauseJob(JobKey.jobKey(jobKey)); break;
            }
        } catch (SchedulerException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("系统异常，暂停失败");
        }
        return ResponseEntity.ok("暂停成功");
    }

    @RequestMapping("/job/start/{jobKey}")
    public ResponseEntity resumeJob(@PathVariable("jobKey")String jobKey){
        try {
            switch (jobKey){
                case "ALL": scheduler.resumeAll(); break;
                default: scheduler.resumeJob(JobKey.jobKey(jobKey));
            }
        } catch (SchedulerException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("系统异常，恢复失败");
        }
        return ResponseEntity.ok("恢复成功");
    }

    @RequestMapping("/job/delete/{jobKey}")
    public ResponseEntity deleteJob(@PathVariable("jobKey")String jobKey){
        try {
            if(!scheduler.checkExists(JobKey.jobKey(jobKey))){
                return ResponseEntity.ok("该job不存在，请刷新重试");
            }
            if(scheduler.deleteJob(JobKey.jobKey(jobKey))){
                return ResponseEntity.ok("删除成功");
            }else{
                return ResponseEntity.badRequest().body("删除失败");
            }
        } catch (SchedulerException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("系统异常");
        }
    }



    @RequestMapping("/trigger/{triggerKey}")
    public ResponseEntity getTrigger(@PathVariable("triggerKey")String triggerKey){
        try {
            CronTriggerImpl trigger = (CronTriggerImpl) scheduler.getTrigger(TriggerKey.triggerKey(triggerKey));
            return ResponseEntity.ok(trigger);
        } catch (SchedulerException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("查询异常");
        }

    }

    @RequestMapping("/trigger/pause/{triggerKey}")
    public ResponseEntity stopTrigger(@PathVariable("triggerKey")String triggerKey){
        try {
            scheduler.pauseTrigger(TriggerKey.triggerKey(triggerKey));
            return ResponseEntity.ok("暂停成功");
        } catch (SchedulerException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("查询异常");
        }
    }

    @RequestMapping("/job/add/page")
    public ModelAndView addJobPage(ModelAndView mav){
        mav.setViewName("/add");
        return mav;
    }

    @RequestMapping("/job/add")
    public ResponseEntity addJob(@Valid ScheduleJob scheduleJob, BindingResult result){
        if(result.hasErrors()){
            return ResponseEntity.ok(result.getAllErrors());
        }

        return null;
    }

    @RequestMapping("/add")
    public ResponseEntity add() throws SchedulerException {
        JobDetail jobDetail = new JobDetailImpl();
        ((JobDetailImpl) jobDetail).setKey(JobKey.jobKey("jordan"));
        ((JobDetailImpl) jobDetail).setDescription("jordan");
        ((JobDetailImpl) jobDetail).setDurability(true);
        ((JobDetailImpl) jobDetail).setJobClass(MyJob.class);
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule("0/10 * * * * ?");
        CronTrigger trigger = TriggerBuilder.newTrigger()
                                .withIdentity("jordan", "DEFAULT")
                                .withSchedule(cronScheduleBuilder).build();
        scheduler.scheduleJob(jobDetail, trigger);

        return ResponseEntity.ok("create success");

    }

    @RequestMapping("/job/edit/{jobKey}")
    public ModelAndView toCronPage(@PathVariable("jobKey")String jobKey, ModelAndView modelAndView) throws SchedulerException {
        JobDetailImpl jobDetail = (JobDetailImpl) scheduler.getJobDetail(JobKey.jobKey(jobKey));
        Trigger trigger = scheduler.getTrigger(TriggerKey.triggerKey(jobKey));
        String cron = ((CronTriggerImpl)trigger).getCronExpression();
        ScheduleJob scheduleJob = new ScheduleJob();
        scheduleJob.setCronExpression(cron);
        BeanUtils.copyProperties(jobDetail, scheduleJob);
        modelAndView.setViewName("/edit");
        modelAndView.addObject("job", scheduleJob);
        return modelAndView;
    }

    @RequestMapping("/job/edit/save/{jobKey}")
    public ResponseEntity saveEdit(@PathVariable("jobKey")String jobKey, @RequestBody ScheduleJob scheduleJob) throws SchedulerException, ParseException {
        JobDetailImpl jobDetail = (JobDetailImpl) scheduler.getJobDetail(JobKey.jobKey(jobKey));
        CronTriggerImpl trigger = (CronTriggerImpl)scheduler.getTrigger(TriggerKey.triggerKey(jobKey));
        jobDetail.setDescription(scheduleJob.getDescription());
        trigger.setCronExpression(scheduleJob.getCronExpression());
        scheduler.deleteJob(JobKey.jobKey(jobKey));
        scheduler.addJob(jobDetail, true);
        scheduler.rescheduleJob(trigger.getKey(), trigger);
        return ResponseEntity.ok("保存成功");
    }

    @RequestMapping("/job/checkCron")
    public ResponseEntity checkCron(@RequestParam("cron")String cron) throws ParseException {
        List<String> result = new ArrayList<String>();
        if (cron == null || cron.length() < 1) {
            return ResponseEntity.ok(result);
        } else {
            CronExpression exppression = new CronExpression(cron);
            DateTimeFormatter formatter =  DateTimeFormatter.ofLocalizedDateTime( FormatStyle.FULL )
                    .withLocale( Locale.CHINA )
                    .withZone( ZoneId.systemDefault());
            Date nowaDate = new Date();
            for(int i = 0; i < 10; i++){
                nowaDate = exppression.getNextValidTimeAfter(nowaDate);
                result.add(formatter.format(nowaDate.toInstant()));
            }

            return ResponseEntity.ok(result);
        }

    }

    @RequestMapping("/cron/page")
    public ModelAndView toCronPage(ModelAndView modelAndView){
        modelAndView.setViewName("/cron");
        return modelAndView;
    }


}
