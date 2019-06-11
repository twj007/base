package com.rabbit.controller;

import com.rabbit.component.RabbitProducer;
import com.rabbit.component.RabbitReceiver;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.ws.Response;

/***
 **@project: base
 **@description:
 **@Author: twj
 **@Date: 2019/06/11
 **/
@RestController
public class RabbitController {

    @Autowired
    private RabbitProducer rabbitProducer;

    @GetMapping("/index")
    public ResponseEntity<String> index(){

        rabbitProducer.sendAll("my msg: " + System.currentTimeMillis());
        return ResponseEntity.ok("ok");
    }
}
