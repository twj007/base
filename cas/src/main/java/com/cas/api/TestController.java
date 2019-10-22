package com.cas.api;

import org.redisson.api.RBucket;
import org.redisson.api.RLock;
import org.redisson.api.RSet;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

/***
 **@project: base
 **@description:
 **@Author: twj
 **@Date: 2019/10/11
 **/
@RequestMapping("/groovy")
@RestController
public class TestController {


    @Autowired
    private RedissonClient redissonClient;

    @RequestMapping("/setValue")
    @ResponseBody
    String setValue(String key) throws Exception{
        if(key != null){
            RLock lock = redissonClient.getLock("redis_lock");
            try {
                lock.lock(5, TimeUnit.SECONDS);
                RBucket<Integer> bucket = redissonClient.getBucket(String.valueOf(key));
                try{
                    int num = bucket.get();
                    if (num > 0) {
                        //扣减库存
                        bucket.set(num - 1);
                        System.out.println("【redisson】 秒杀成功， 等待生成订单");
                        System.out.println("【数量】: "+ String.valueOf(num-1));
                        return "秒杀成功";

//                        }
                    } else {
                        System.out.println("【redisson】 库存不足");
                        return "库存不足";
                    }
                }catch (Exception e){
                    System.out.println("【exception】 msg:{}"+e.getStackTrace());
                    return "系统繁忙，请刷新重试";
                }finally {
                    lock.unlock();
                }
            }catch(Exception e) {
                System.out.println("【秒杀】线程中断:{}"+ e.getStackTrace());
                return "系统繁忙，请刷新重试";

            }
        }else{
            return "请输入参数";
        }
    }
}
