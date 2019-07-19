package com.framework.web.dao;

import com.framework.web.pojo.User;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface WebDao {

    List<User> listUser();
}
