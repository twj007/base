package com.rabbit.controller;

import org.redisson.api.RBucket;
import org.redisson.api.RReadWriteLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/***
 **@project: base
 **@description:
 **@Author: twj
 **@Date: 2019/06/12
 **/
@RestController
public class RabbitController {

    private static final Logger logger = LoggerFactory.getLogger(RabbitController.class);

    @Autowired
    private RedissonClient redissonClient;

    @Value("${spring.redis.lock.key}")
    private String key;

    @GetMapping("/index")
    public ResponseEntity index(String item_id){
        RReadWriteLock lock = redissonClient.getReadWriteLock(key);


        try{
            lock.writeLock().lock(10, TimeUnit.SECONDS);
            RBucket<Integer> value = redissonClient.getBucket(item_id);
            Integer amounts = value.get();
            if(amounts.intValue() > 0){
                value.set(amounts - 1);

                logger.info("【秒杀】：秒杀成功： {}: 剩余库存： {}", item_id, amounts - 1);

            }else{
                logger.info("【秒杀】： 库存已空，秒杀失败");

            }

        } catch(Exception e){
            logger.error("【获取redis锁失败】：{}", item_id);
            return ResponseEntity.ok("系统繁忙，请稍后再试");
        }finally{
            lock.writeLock().unlock();
        }

        return ResponseEntity.ok("ok");
    }


    @GetMapping("/index2")
    public ResponseEntity index2(String item_id){

        RBucket<Integer> value = redissonClient.getBucket(item_id);
        System.out.println(value.get());
        return ResponseEntity.ok(value.get());
    }
}
