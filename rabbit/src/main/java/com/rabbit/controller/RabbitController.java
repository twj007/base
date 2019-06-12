package com.rabbit.controller;

import com.rabbit.component.RabbitProducer;
import com.rabbit.utils.ResultBody;
import com.rabbit.utils.Results;
import org.redisson.api.RBucket;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;


/***
 **@project: base
 **@description:
 **@Author: twj
 **@Date: 2019/06/11
 **/
@RestController
public class RabbitController {

    private static final Logger logger = LoggerFactory.getLogger(RabbitController.class);

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private RabbitProducer rabbitProducer;

    @GetMapping("/index")
    public ResultBody<Object> index(String item_id){
        RLock lock = redissonClient.getLock(item_id);
        ResultBody body = null;

        try{
            lock.lock(Long.valueOf(30), TimeUnit.SECONDS);

            RBucket<Long> amounts = redissonClient.getBucket(item_id);
            if(amounts.get().intValue() > 0){
                amounts.trySet(amounts.get() - 1);
                body = Results.SUCCESS.result("秒杀成功", null);
                logger.info("【秒杀】：秒杀成功： {}: 剩余库存： {}", item_id, amounts.get() - 1);
                rabbitProducer.sendAll(item_id);
            }else{
                    logger.info("【秒杀】： 库存已空，秒杀失败");
                    body = Results.SOLD_OUT.result("该物品已秒杀完", null);
            }

//            }else{
//                return Results.FORBIDDEN.result("系统繁忙，请稍后再试", null);
//            }
        }catch(Exception e){
            logger.error("【获取redis锁失败】：{}", item_id);
            return Results.FORBIDDEN.result("系统繁忙，请稍后再试", null);
        }finally{
            lock.unlock();
        }

        return body;
    }
}
