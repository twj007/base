package com.demo.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/***
 **@project: base
 **@description:
 **@Author: twj
 **@Date: 2019/10/21
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderPage {
    private int pageSize;
    private int pageNum;
    private List<?> page;
    private int total;
}
