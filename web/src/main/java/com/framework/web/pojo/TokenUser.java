package com.framework.web.pojo;

import java.io.Serializable;

/***
 **@project: base
 **@description:
 **@Author: twj
 **@Date: 2019/07/19
 **/
public class TokenUser implements Serializable {

    private String uid;

    private String username;

    private String nickname;

    private String accessToken;

    private String someMsg;


    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getSomeMsg() {
        return someMsg;
    }

    public void setSomeMsg(String someMsg) {
        this.someMsg = someMsg;
    }
}
