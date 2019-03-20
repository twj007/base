package com.framework.web.service;

import com.framework.web.dao.WebDao;
import com.framework.web.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WebService {

    @Autowired
    private WebDao webDao;

    public String test(){
        return "ok";
    }

    public List<User> test2(){
        return webDao.listUser();
    }
}
