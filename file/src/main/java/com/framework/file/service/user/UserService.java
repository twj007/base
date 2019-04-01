package com.framework.file.service.user;

import com.framework.file.dao.user.UserDao;
import com.framework.file.pojo.user.Menu;
import com.framework.file.pojo.user.SysUser;
import com.framework.file.pojo.user.User;
import com.framework.file.util.TreeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service("userService")
public class UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Long checkUserExists(SysUser user) {
        return userDao.checkUserExists(user);
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public User login(User user) {
        return userDao.login(user);
    }

    /****
     * 邮件服务
     * @param user
     * @return
     */
    public String sendValidEmail(User user) {
        return "true";
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public boolean register(User user) {
        userDao.register(user);
        if(user.getId() != null){
            return true;
        }
        return false;
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public boolean updateUser(User user) {
        Long num = userDao.updateUser(user);
        if(num == 1){
            return true;
        }
        return false;
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public boolean delUser(User user) {
        Long num = userDao.deleteUser();
        if(num == 1){
            return true;
        }
        return false;
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<User> getContactList(User u) {
        List<User> userList = userDao.getContactList(u);
        return userList;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void batchRegister() {
        List<SysUser> users = userDao.getListUser();
        for(SysUser user : users){
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        userDao.updateUsers(users);
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<Menu> getMenu(SysUser user) {
        List<Menu> menus = userDao.getMenu(user);
        return TreeUtils.getChildPerms(menus, 0);
    }
}
