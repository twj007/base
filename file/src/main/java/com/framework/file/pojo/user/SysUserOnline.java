package com.framework.file.pojo.user;


public class SysUserOnline {

  private String sessionId;
  private String loginName;
  private String deptName;
  private String ipaddr;
  private String loginLocation;
  private String browser;
  private String os;
  private String status;
  private java.sql.Timestamp startTimestsamp;
  private java.sql.Timestamp lastAccessTime;
  private long expireTime;


  public String getSessionId() {
    return sessionId;
  }

  public void setSessionId(String sessionId) {
    this.sessionId = sessionId;
  }


  public String getLoginName() {
    return loginName;
  }

  public void setLoginName(String loginName) {
    this.loginName = loginName;
  }


  public String getDeptName() {
    return deptName;
  }

  public void setDeptName(String deptName) {
    this.deptName = deptName;
  }


  public String getIpaddr() {
    return ipaddr;
  }

  public void setIpaddr(String ipaddr) {
    this.ipaddr = ipaddr;
  }


  public String getLoginLocation() {
    return loginLocation;
  }

  public void setLoginLocation(String loginLocation) {
    this.loginLocation = loginLocation;
  }


  public String getBrowser() {
    return browser;
  }

  public void setBrowser(String browser) {
    this.browser = browser;
  }


  public String getOs() {
    return os;
  }

  public void setOs(String os) {
    this.os = os;
  }


  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }


  public java.sql.Timestamp getStartTimestsamp() {
    return startTimestsamp;
  }

  public void setStartTimestsamp(java.sql.Timestamp startTimestsamp) {
    this.startTimestsamp = startTimestsamp;
  }


  public java.sql.Timestamp getLastAccessTime() {
    return lastAccessTime;
  }

  public void setLastAccessTime(java.sql.Timestamp lastAccessTime) {
    this.lastAccessTime = lastAccessTime;
  }


  public long getExpireTime() {
    return expireTime;
  }

  public void setExpireTime(long expireTime) {
    this.expireTime = expireTime;
  }

}
