package com.rabbit.controller;

import com.rabbit.component.RabbitProducer;
import com.rabbit.utils.ResultBody;
import com.rabbit.utils.Results;
import org.redisson.api.RBucket;
import org.redisson.api.RLock;
import org.redisson.api.RReadWriteLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;


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

    @Value("${spring.redis.lock.key}")
    private String key;

    @GetMapping("/index")
    public ResultBody<Object> index(String item_id){
        RReadWriteLock lock = redissonClient.getReadWriteLock(key);
        ResultBody body = null;

        try{
            lock.writeLock().lock(10, TimeUnit.SECONDS);
            RBucket<Integer> value = redissonClient.getBucket(item_id);
            Integer amounts = value.get();
            if(amounts.intValue() > 0){
                value.set(amounts - 1);
                body = Results.SUCCESS.result("秒杀成功", null);
                logger.info("【秒杀】：秒杀成功： {}: 剩余库存： {}", item_id, amounts - 1);
                rabbitProducer.sendOrder(item_id);
            }else{
                logger.info("【秒杀】： 库存已空，秒杀失败");
                body = Results.SOLD_OUT.result("该物品已秒杀完", null);
            }

        } catch(Exception e){
            logger.error("【获取redis锁失败】：{}", item_id);
            return Results.FORBIDDEN.result("系统繁忙，请稍后再试", null);
        }finally{
            lock.writeLock().unlock();
        }

        return body;
    }

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

    /***
     * 锁超时下可能会脏读
     * @param item_id
     * @return
     */
    @GetMapping("/index2")
    public ResultBody<Integer> index2(String item_id){
        RBucket<Integer> value = redissonClient.getBucket(item_id);
        return Results.SUCCESS.result("success", value.get());
    }
}
