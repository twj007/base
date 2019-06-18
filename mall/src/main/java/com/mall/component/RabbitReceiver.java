package com.mall.component;


import com.mall.config.RabbitConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/***
 **@project: base
 **@description:
 **@Author: twj
 **@Date: 2019/06/11
 **/
@Component
@RabbitListener(queues = RabbitConfig.QUEUE_A)
//@RabbitListener(queues = {RabbitConfig.QUEUE_B, RabbitConfig.QUEUE_C})
public class RabbitReceiver {

    private static final Logger logger = LoggerFactory.getLogger(RabbitReceiver.class);
    static int count = 0;
    @RabbitHandler
    public void process(String content){
        count ++;
        logger.info("【队列msg】: {} {}", content, count);
    }

}
