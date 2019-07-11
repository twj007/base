package com.mall.model;

import java.io.Serializable;
import java.util.Date;

/***
 **@project: base
 **@description: request recode
 **@Author: twj
 **@Date: 2019/06/21
 **/
public class RequestRecord implements Serializable {

    private Long id;

    private String visitor;

    private String uri;

    private String queryString;

    private String address;

    private String host;

    private int port;

    private Date createDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVisitor() {
        return visitor;
    }

    public void setVisitor(String visitor) {
        this.visitor = visitor;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getQueryString() {
        return queryString;
    }

    public void setQueryString(String queryString) {
        this.queryString = queryString;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getHost() {
        return host;
    }


    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Override
    public String toString() {
        return "RequestRecord{" +
                "id=" + id +
                ", visitor='" + visitor + '\'' +
                ", uri='" + uri + '\'' +
                ", queryString='" + queryString + '\'' +
                ", address='" + address + '\'' +
                ", host='" + host + '\'' +
                ", port=" + port +
                ", createDate=" + createDate +
                '}';
    }


}
