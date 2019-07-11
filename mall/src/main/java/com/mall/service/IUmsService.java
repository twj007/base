package com.mall.service;

import com.mall.pojo.UmsMember;

import java.util.List;

public interface IUmsService {

     List<UmsMember> getAll();

    List<UmsMember> getRemoteAll();
}
