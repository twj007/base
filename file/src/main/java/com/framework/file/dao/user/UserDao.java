package com.framework.file.dao.user;

import com.framework.file.pojo.user.Menu;
import com.framework.file.pojo.user.SysRole;
import com.framework.file.pojo.user.SysUser;
import com.framework.file.pojo.user.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao {
    Long checkUserExists(SysUser user);

    User login(User user);

    void register(User user);

    Long updateUser(User user);

    Long deleteUser();

    List<User> getContactList(User u);

    SysUser authUser(String s);

    List<SysUser> getListUser();

    void updateUsers(List<SysUser> users);

    List<Menu> getMenu(SysUser user);

    List<Menu> getAllMenu();

    List<Menu> getMenuByparent(Long parentId);

    List<SysRole> getUserRoles(Long userId);
}
