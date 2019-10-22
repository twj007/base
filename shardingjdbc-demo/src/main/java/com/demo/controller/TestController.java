package com.demo.controller;

import com.demo.dao.OrderMapper;
import com.demo.pojo.Order;
import com.demo.pojo.OrderPage;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/***
 **@project: base
 **@description:
 **@Author: twj
 **@Date: 2019/10/21
 **/
@RequestMapping("/sharding")
@RestController
@Slf4j
public class TestController {

    @Autowired
    private OrderMapper orderMapper;

    @GetMapping("/test")
    public String test(){
        return "ok";
    }

    @GetMapping("/findAll")
    public ResponseEntity<OrderPage> findAll(Integer pageSize, Integer pageNum){

        Page page = PageHelper.startPage(pageNum, pageSize);
        List<Order> orders = orderMapper.findAll();
        Long total = page.getTotal();
        OrderPage orderPage = new OrderPage().builder().page(orders).pageSize(pageSize).pageNum(pageNum).total(total.intValue()).build();
        return ResponseEntity.ok(orderPage);
    }

    @GetMapping("/findOne")
    public ResponseEntity findOne(Order order, int pageSize, int pageNum){
        Page page = PageHelper.startPage(pageNum, pageSize);
        List<Order> o = orderMapper.findByOrder(order);
        Long total = page.getTotal();
        OrderPage orderPage = new OrderPage().builder().pageNum(pageNum).pageSize(pageSize).page(o).total(total.intValue()).build();
        return ResponseEntity.ok(orderPage);
    }

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/insert")
    public ResponseEntity<Order> insert(Long userId, Long orderId){

//        Long id = restTemplate.getForObject("http://47.100.206.158:8080/api/segment/get/leaf-segment-test", java.lang.Long.class);
        Long id = restTemplate.getForObject("http://47.100.206.158:8080/api/snowflake/get/test", java.lang.Long.class);
        Order order = Order.builder().id(id).userId(userId).orderId(orderId).build();
        int num = orderMapper.insert(order);
        if(num > 0){
            return ResponseEntity.ok(order);
        }
        return ResponseEntity.badRequest().body(order);
    }

    @GetMapping("/update/{id}")
    public ResponseEntity updateOrderById(@PathVariable("id") Long id){

        return ResponseEntity.ok(null);
    }
}
