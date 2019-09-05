package com.cas.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

/***
 **@project: base
 **@description:
 **@Author: twj
 **@Date: 2019/09/05
 **/
@Getter
@Setter
@ToString
@Entity(name = "sys_user")
public class SysUser {

    @JsonProperty("id")
    @Id
    private Long id;

    @NotNull
    @Column(name = "username")
    private String username;

    @JsonProperty("@class")
    @Transient
    //需要返回实现org.apereo.cas.authentication.principal.Principal的类名接口
    private String clazz = "org.apereo.cas.authentication.principal.SimplePrincipal";

    @JsonProperty("attributes")
    @Transient
    private Map<String, Object> attributes = new HashMap<>();

    @JsonIgnore
    @Column(name = "password")
    private String password;

    @JsonIgnore
    @Transient
    //用户是否不可用
    private boolean disabled = false;

    @JsonIgnore
    @Transient
    //用户是否过期
    private boolean expired = false;

    @JsonIgnore
    @Transient
    //是否锁定
    private boolean locked = false;

    /** 省略getter和setter 自行补充**/
}
