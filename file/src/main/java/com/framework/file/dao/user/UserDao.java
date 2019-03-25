package com.framework.file.dao.user;

import com.framework.file.pojo.user.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao {
    Long checkUserExists(User user);

    User login(User user);

    void register(User user);

    Long updateUser(User user);

    Long deleteUser();

    List<User> getContactList(User u);

    User authUser(String s);
}
