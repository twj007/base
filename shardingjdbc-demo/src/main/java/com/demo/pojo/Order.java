package com.demo.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/***
 **@project: base
 **@description:
 **@Author: twj
 **@Date: 2019/10/21
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order implements Serializable {
    private Long id;
    private Long userId;
    private Long orderId;
}
