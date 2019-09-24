package com.cas.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/***
 **@project: base
 **@description:
 **@Author: twj
 **@Date: 2019/09/24
 **/
@Getter
@Setter
@ToString
public class Org {

    private Long orgId;
    private String orgCode;
    private Integer orgLevel;
    private String orgName;
    private Long orgParentId;
    private String state;
    private String remark;
    private Date lastModify;
    private String rootNotice;
    private String companyId;

}
