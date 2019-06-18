package com.mall.controller;

import com.mall.util.ResultBody;
import com.mall.util.Results;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;

/***
 **@project: base
 **@description: user manage system
 **@Author: twj
 **@Date: 2019/06/18
 **/
@RestController
public class UmsController {

    private static final Logger logger = LoggerFactory.getLogger(UmsController.class);

    @Value("${spring.redis.lock.key}")
    private String key;

    @Autowired
    private RedissonClient redissonClient;


    @GetMapping("/test")
    public ResultBody<String> test(){
        ReadWriteLock lock = redissonClient.getReadWriteLock(key);
        RBucket<String> test = null;
        String value = "";
        try{
            test = redissonClient.getBucket("test");
            lock.writeLock().tryLock();
            test.trySet("ok");
            value = test.get();
        } catch (Exception e) {
            logger.error("【redis interrupted】: {}", e.fillInStackTrace());
            e.printStackTrace();
        } finally {
            lock.writeLock().unlock();
        }
        return Results.SUCCESS.result("ok", value);
    }
}
