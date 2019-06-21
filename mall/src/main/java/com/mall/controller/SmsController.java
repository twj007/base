package com.mall.controller;

import com.mall.component.RabbitProducer;
import com.mall.pojo.PmsProduct;
import com.mall.pojo.SmsFlashPromotion;
import com.mall.pojo.SmsFlashPromotionProductRelation;
import com.mall.service.ISmsService;
import com.mall.util.ResultBody;
import com.mall.util.Results;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.redisson.api.RBucket;
import org.redisson.api.RSet;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.locks.Lock;

/***
 **@project: base
 **@description: miao sha demo
 **@Author: twj
 **@Date: 2019/06/20
 **/
@RestController
@RequestMapping("/mall")
public class SmsController {

    private static final Logger logger = LoggerFactory.getLogger(SmsController.class);

    @Value("${spring.redis.lock.key}")
    private String key;

    @Autowired
    private RabbitProducer producer;

    @Autowired
    private RedissonClient redissonClient;


    @Autowired
    private ISmsService smsService;

    /***
     * 获取全部秒杀活动
     * @return
     */
    @GetMapping("/getFlashActivities")
    @ApiOperation(value ="获取秒杀活动", notes = "获取秒杀活动列表")
    public ResultBody<List<SmsFlashPromotion>> getFlashActivities(){
        return Results.SUCCESS.result("ok", smsService.getAllFlashActivities());
    }

    /***
     * 获取单个秒杀活动
     * @param promotion
     * @return
     */
    @GetMapping("/getFlashActivity")
    @ApiOperation(value ="获取单个秒杀活动", notes = "获取单个秒杀活动")
    @ApiImplicitParam(value = "秒杀消息", name = "promotion")
    public ResultBody<SmsFlashPromotion> getFlashActivity(SmsFlashPromotion promotion){
        return Results.SUCCESS.result("ok", smsService.getFlashActivity(promotion.getId()));
    }

    @GetMapping("/flashPromotion")
    public ResultBody<String> flashPromotion(SmsFlashPromotionProductRelation product){
        logger.info("【miaosha】 userId: {} productId: {}", product.getUserId(), product.getProductId());
        Lock lock = redissonClient.getLock(key);
        //tryLock一定要加解锁时间，不然可能死锁
        //if(lock.tryLock()){

            lock.lock();
            RBucket<Integer> bucket = redissonClient.getBucket(String.valueOf(product.getProductId()));
            Integer nums = bucket.get();
            if(nums > 0){
                RSet<Long> products =  redissonClient.getSet("user_"+String.valueOf(product.getUserId()));
                if(products != null && products.contains(product.getProductId())){
                    logger.info("【redis】 该用户已秒杀该产品");
                    lock.unlock();
                    return Results.BAD__REQUEST.result("您已秒杀该产品", null);
                }else{
                    //扣减库存
                    bucket.set(nums - 1);
                    //写入用户明细
                    products.add(product.getProductId());

                    product.setFlashPromotionCount(nums.intValue() - 1);
                    producer.send(product);

                    logger.info("【redisson】 秒杀成功， 等待生成订单");
                    lock.unlock();
                    return Results.SUCCESS.result("秒杀成功", null);

                }
            }else{
                logger.info("【redisson】 库存不足");
                lock.unlock();
                return Results.BAD__REQUEST.result("库存不足", null);
            }

//        }else{
//            logger.error("【lock】 获取锁失败");
//            return Results.BAD__REQUEST.result("系统繁忙，请刷新重试", null);
//        }

    }
}
