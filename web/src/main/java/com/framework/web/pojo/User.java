package com.framework.web.pojo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@ToString
public class User implements Serializable {

    private Integer id;

    @NotEmpty(message = "用户名不为空")
    private String name;

    @NotEmpty
    @Length(min = 6, message = "密码不少于6位")
    private String password;

    private String email;

    private String phone;

    private String birthday;

    private Integer age;

    private String status;

    private String address;

    private Date creatTime;

    private Date modifyTime;

    private Integer roleId;


    private Integer orgId;
}
