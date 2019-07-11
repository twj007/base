package com.framework.file.pojo.user;


public class SysJobLog {

  private long jobLogId;
  private String jobName;
  private String jobGroup;
  private String methodName;
  private String methodParams;
  private String jobMessage;
  private String status;
  private String exceptionInfo;
  private java.sql.Timestamp createTime;


  public long getJobLogId() {
    return jobLogId;
  }

  public void setJobLogId(long jobLogId) {
    this.jobLogId = jobLogId;
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


  public String getMethodName() {
    return methodName;
  }

  public void setMethodName(String methodName) {
    this.methodName = methodName;
  }


  public String getMethodParams() {
    return methodParams;
  }

  public void setMethodParams(String methodParams) {
    this.methodParams = methodParams;
  }


  public String getJobMessage() {
    return jobMessage;
  }

  public void setJobMessage(String jobMessage) {
    this.jobMessage = jobMessage;
  }


  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }


  public String getExceptionInfo() {
    return exceptionInfo;
  }

  public void setExceptionInfo(String exceptionInfo) {
    this.exceptionInfo = exceptionInfo;
  }


  public java.sql.Timestamp getCreateTime() {
    return createTime;
  }

  public void setCreateTime(java.sql.Timestamp createTime) {
    this.createTime = createTime;
  }

}
