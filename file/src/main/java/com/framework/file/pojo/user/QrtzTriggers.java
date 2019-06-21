package com.framework.file.pojo.user;


public class QrtzTriggers {

  private String schedName;
  private String triggerName;
  private String triggerGroup;
  private String jobName;
  private String jobGroup;
  private String description;
  private long nextFireTime;
  private long prevFireTime;
  private long priority;
  private String triggerState;
  private String triggerType;
  private long startTime;
  private long endTime;
  private String calendarName;
  private long misfireInstr;
  private String jobData;


  public String getSchedName() {
    return schedName;
  }

  public void setSchedName(String schedName) {
    this.schedName = schedName;
  }


  public String getTriggerName() {
    return triggerName;
  }

  public void setTriggerName(String triggerName) {
    this.triggerName = triggerName;
  }


  public String getTriggerGroup() {
    return triggerGroup;
  }

  public void setTriggerGroup(String triggerGroup) {
    this.triggerGroup = triggerGroup;
  }


  public String getJobName() {
    return jobName;
  }

  public void setJobName(String jobName) {
    this.jobName = jobName;
  }


  public String getJobGroup() {
    return jobGroup;
  }

  public void setJobGroup(String jobGroup) {
    this.jobGroup = jobGroup;
  }


  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }


  public long getNextFireTime() {
    return nextFireTime;
  }

  public void setNextFireTime(long nextFireTime) {
    this.nextFireTime = nextFireTime;
  }


  public long getPrevFireTime() {
    return prevFireTime;
  }

  public void setPrevFireTime(long prevFireTime) {
    this.prevFireTime = prevFireTime;
  }


  public long getPriority() {
    return priority;
  }

  public void setPriority(long priority) {
    this.priority = priority;
  }


  public String getTriggerState() {
    return triggerState;
  }

  public void setTriggerState(String triggerState) {
    this.triggerState = triggerState;
  }


  public String getTriggerType() {
    return triggerType;
  }

  public void setTriggerType(String triggerType) {
    this.triggerType = triggerType;
  }


  public long getStartTime() {
    return startTime;
  }

  public void setStartTime(long startTime) {
    this.startTime = startTime;
  }


  public long getEndTime() {
    return endTime;
  }

  public void setEndTime(long endTime) {
    this.endTime = endTime;
  }


  public String getCalendarName() {
    return calendarName;
  }

  public void setCalendarName(String calendarName) {
    this.calendarName = calendarName;
  }


  public long getMisfireInstr() {
    return misfireInstr;
  }

  public void setMisfireInstr(long misfireInstr) {
    this.misfireInstr = misfireInstr;
  }


  public String getJobData() {
    return jobData;
  }

  public void setJobData(String jobData) {
    this.jobData = jobData;
  }

}
