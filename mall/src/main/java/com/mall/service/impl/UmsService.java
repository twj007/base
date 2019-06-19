package com.mall.service.impl;


import com.mall.dao.UmsMemberMapper;
import com.mall.pojo.UmsMember;
import com.mall.service.IUmsService;
import com.mall.util.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/***
 **@project: base
 **@description:
 **@Author: twj
 **@Date: 2019/06/19
 **/
@Service
public class UmsService implements IUmsService {

    @Autowired
    private UmsMemberMapper umsMemberMapper;

    @Override
    @DataSource(type = "master")
    public List<UmsMember> getAll() {
        List<UmsMember> members = umsMemberMapper.selectAll();
        return members;
    }

    @Override
    @DataSource(type = "slaver")
    public List<UmsMember> getRemoteAll() {
        List<UmsMember> members = umsMemberMapper.selectAll();
        return members;
    }
}
