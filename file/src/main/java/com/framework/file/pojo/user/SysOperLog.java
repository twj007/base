package com.framework.file.pojo.user;


public class SysOperLog {

  private long operId;
  private String title;
  private String action;
  private String method;
  private String channel;
  private String operName;
  private String deptName;
  private String operUrl;
  private String operIp;
  private String operLocation;
  private String operParam;
  private String status;
  private String errorMsg;
  private java.sql.Timestamp operTime;


  public long getOperId() {
    return operId;
  }

  public void setOperId(long operId) {
    this.operId = operId;
  }


  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }


  public String getAction() {
    return action;
  }

  public void setAction(String action) {
    this.action = action;
  }


  public String getMethod() {
    return method;
  }

  public void setMethod(String method) {
    this.method = method;
  }


  public String getChannel() {
    return channel;
  }

  public void setChannel(String channel) {
    this.channel = channel;
  }


  public String getOperName() {
    return operName;
  }

  public void setOperName(String operName) {
    this.operName = operName;
  }


  public String getDeptName() {
    return deptName;
  }

  public void setDeptName(String deptName) {
    this.deptName = deptName;
  }


  public String getOperUrl() {
    return operUrl;
  }

  public void setOperUrl(String operUrl) {
    this.operUrl = operUrl;
  }


  public String getOperIp() {
    return operIp;
  }

  public void setOperIp(String operIp) {
    this.operIp = operIp;
  }


  public String getOperLocation() {
    return operLocation;
  }

  public void setOperLocation(String operLocation) {
    this.operLocation = operLocation;
  }


  public String getOperParam() {
    return operParam;
  }

  public void setOperParam(String operParam) {
    this.operParam = operParam;
  }


  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }


  public String getErrorMsg() {
    return errorMsg;
  }

  public void setErrorMsg(String errorMsg) {
    this.errorMsg = errorMsg;
  }


  public java.sql.Timestamp getOperTime() {
    return operTime;
  }

  public void setOperTime(java.sql.Timestamp operTime) {
    this.operTime = operTime;
  }

}
