package com.cas.dao;

import com.cas.dto.SysUser;
import org.springframework.data.repository.Repository;

/***
 **@project: base
 **@description:
 **@Author: twj
 **@Date: 2019/09/05
 **/
@org.springframework.stereotype.Repository
public interface UserRepository extends Repository<SysUser, Long> {

    SysUser findByUsername(String username);
}
