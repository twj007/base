package com.framework.file.pojo.user;

import com.framework.file.pojo.OperationType;
import lombok.*;
import org.apache.ibatis.type.Alias;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
//@Alias("User")
public class User extends OperationType {

    private Long id;

    private String username;

    private String password;

    private String email;

    private int age;

    private String mobile;

    private String job;

    private String imgUrl;
}
