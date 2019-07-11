package com.framework.pay.component;

import com.framework.pay.config.RabbitConfig;
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
//@RabbitListener(queues = RabbitConfig.QUEUE_A)
@RabbitListener(queues = {RabbitConfig.QUEUE_B, RabbitConfig.QUEUE_C})
public class RabbitReceiver {

    private static final Logger logger = LoggerFactory.getLogger(RabbitReceiver.class);

    @RabbitHandler
    public void process(String content){
        // 1.查数据库订单的状态
        // 如果已发货，要通知
        // 没发货直接修改订单状态
        logger.info("【队列msg】: {}", content);
    }

}
