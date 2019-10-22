package com.framework.web.webService;

import com.framework.web.pojo.User;
import java.io.File;

/***
 **@project: base
 **@description:
 **@Author: twj
 **@Date: 2019/10/10
 **/
public class testServiceImpl implements TestService {
    @Override
    public User test(String data) {
        User user = new User();
        user.setName(data);
        return user;
    }

    @Override
    public User t2(File file) {
        return null;
    }
}
