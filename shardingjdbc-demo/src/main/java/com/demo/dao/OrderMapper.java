package com.demo.dao;

import com.demo.pojo.Order;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/***
 **@project: base
 **@description:
 **@Author: twj
 **@Date: 2019/10/21
 **/
@Mapper
public interface OrderMapper {

    @Select("select * from order order")
    List<Order> findAll();

    @Insert("insert into order(id, user_id, order_id) values (#{id}, #{userId}, #{orderId})")
    int insert(Order order);

    @Select("<script>" +
            "select id, user_id, order_id from order" +
            "<where>" +
            "<if test='order.id != null'>id=#{order.id} </if>" +
            "<if test='order.userId != null'>user_id=#{order.userId} </if>" +
            "<if test='order.orderId != null'>order_id=#{order.orderId} </if>" +
            "</where>" +
            "</script>")
    List<Order> findByOrder(@Param("order")Order order);
}
