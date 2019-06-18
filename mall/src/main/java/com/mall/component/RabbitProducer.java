package com.mall.component;


import com.mall.config.RabbitConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.stereotype.Component;

import java.util.UUID;

/***
 **@project: base
 **@description:
 **@Author: twj
 **@Date: 2019/06/11
 * 消息生产者
 **/
@Component
public class RabbitProducer implements RabbitTemplate.ConfirmCallback {

    private static final Logger logger = LoggerFactory.getLogger(RabbitProducer.class);

    private  RabbitTemplate rabbitTemplate;

    /***
     * 设置回调为该类的confirm方法
     * @param rabbitTemplate
     */
    public RabbitProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
        rabbitTemplate.setConfirmCallback(this);
    }

    /****
     *
     * @param msg work模式下，通过交换机和routekey向对应的队列发布消息 发布订阅
     * @return
     */
    public void send(String msg){
        logger.info("【send msg】: {}", msg);
        CorrelationData data  = new CorrelationData(UUID.randomUUID().toString());
        rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE_A, RabbitConfig.ROUTINGKEY_A, msg, data);
    }

    /***
     * 广播队列消息发送 绑定了对应交换机的队列都会接收到下列消息
     * @param msg
     */
    public void sendAll(String msg){
        logger.info("【send all】: {}", msg);
        CorrelationData data = new CorrelationData(UUID.randomUUID().toString());
        rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE_B, "", msg, data);
    }

    public void sendOrder(Object obj){
        logger.info("【send order】: {}" + obj);
        CorrelationData data = new CorrelationData(UUID.randomUUID().toString());
        rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE_B, "", obj, data);
    }

    /****
     * 发送消息后的回调 b为true 发送成功且接收成功 b为false 接收失败
     * @param correlationData
     * @param b
     * @param s
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean b, String s) {
        logger.info("【final callback】: {}", s);
        if(b){
            logger.info("【result】: send success");
        }else{
            logger.error("【send error】: {}", s);
        }
    }



}
