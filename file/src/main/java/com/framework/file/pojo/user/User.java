package com.framework.file.pojo.user;

import com.framework.file.pojo.OperationType;
import lombok.*;
import org.apache.ibatis.type.Alias;

import javax.validation.constraints.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
//@Alias("User")
public class User extends OperationType {

    private Long id;

    @NotEmpty(message = "用户名不为空")
    @NotBlank(message = "用户名不为空")
    private String username;

    @Size(min = 6, message = "最小不少于{min}个字母")
    @NotEmpty(message = "密码不为空")
    @NotBlank(message = "密码不为空")
    private String password;

//    @NotBlank(message = "邮箱不能为空")
//    @Pattern(regexp = "/", message = "格式不正确")
    private String email;

//    @NotNull(message = "年龄有效范围在之间")
//    @Min(value = 18, message = "年龄不小于{value}岁")
    private int age;

    private String mobile;

    private String job;

    private String imgUrl;
}
