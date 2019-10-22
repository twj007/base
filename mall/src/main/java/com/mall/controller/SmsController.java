package com.mall.controller;

import com.google.common.base.Stopwatch;
import com.mall.component.RabbitProducer;
import com.mall.dao.SmsFlashPromotionProductRelationMapper;
import com.mall.pojo.PmsProduct;
import com.mall.pojo.SmsFlashPromotion;
import com.mall.pojo.SmsFlashPromotionProductRelation;
import com.mall.service.ISmsService;
import com.mall.util.DataSource;
import com.mall.util.ResultBody;
import com.mall.util.Results;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.redisson.RedissonKeys;
import org.redisson.api.*;
import org.redisson.codec.SerializationCodec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
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

    private AtomicInteger count = new AtomicInteger(0);

    @GetMapping("/count")
    public ResponseEntity count(){

        producer.sendMessage(count.getAndIncrement());
        return ResponseEntity.ok("ok");
    }

    @GetMapping("/email")
    public ResponseEntity sendMail(String email){
        producer.sendEmail(email);
        return ResponseEntity.ok("发送中请稍候");
    }


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

    @Autowired
    private SmsFlashPromotionProductRelationMapper relationMapper;

    @GetMapping("/testInser")
    @DataSource
    public ResponseEntity testConcurrentInsert(SmsFlashPromotionProductRelation relation){
        int num = relationMapper.saveOrder(relation);
        return ResponseEntity.ok(num);
    }

    /***
     * 支付完成后回调接口
     * @param product
     * @return
     */
    @RequestMapping("/finishOrder")
    public ResultBody finishOrder(SmsFlashPromotionProductRelation product){
        logger.info("【支付成功回调】 第三方支付完成后，返回支付完成消息： {}", product);
        product.setStatus("2");
        int num = smsService.cancelOrder(product);
        if(num == 0){
            return Results.SUCCESS.result("订单号不存在", "");
        }
        RBlockingQueue blockingQueue = redissonClient.getBlockingQueue("order_delay_queue", new SerializationCodec());
        RDelayedQueue queue = redissonClient.getDelayedQueue(blockingQueue);
        if(queue.remove(product.getId())) {
            return Results.SUCCESS.result("支付成功，等待商家发货", "");
        }else{
            return Results.SUCCESS.result("请勿重复支付", "");
        }

    }

    @GetMapping("/flashPromotion")
    public ResultBody<String> flashPromotion(SmsFlashPromotionProductRelation product){
        //logger.info("【秒杀】 userId: {} productId: {}", product.getUserId(), product.getProductId());
        logger.info("【秒杀】 productId: {}",  product.getProductId());

        RLock lock = redissonClient.getLock(key);
        //tryLock一定要加解锁时间，不然可能死锁
        //if(lock.tryLock()){
        try {
//            if (lock.tryLock(2000, TimeUnit.MILLISECONDS)) {
                lock.lock(10L, TimeUnit.SECONDS);
                RBucket<Integer> bucket = redissonClient.getBucket(String.valueOf(product.getProductId()));
                try{
                    int num = bucket.get();
                    if (num > 0) {
                        RSet<Long> products = redissonClient.getSet("user_" + String.valueOf(product.getUserId()));
                        if (products != null && products.contains(product.getProductId())) {
                            logger.info("【redis】 该用户已秒杀该产品");
                            return Results.BAD__REQUEST.result("您已秒杀该产品", null);
                        } else {
                            //扣减库存
                            bucket.set(num - 1);
                            //写入用户明细
                            products.add(product.getProductId());
                            products.expire(5, TimeUnit.MINUTES);
                            product.setFlashPromotionCount(num - 1);
                            //异步保存数据
                            //producer.send(product);
                            //延迟队列 15分钟内未移除出队列，进入延迟队列
                            //保存订单信息并且将订单号传入延时队列，支付接口完成时通过订单号把队列中的数据移除
                            RBlockingQueue blockingQueue = redissonClient.getBlockingQueue("order_delay_queue", new SerializationCodec());
                            RDelayedQueue queue = redissonClient.getDelayedQueue(blockingQueue);
                            smsService.saveOrder(product);
                            queue.offer(product.getId(), 1, TimeUnit.MINUTES);
                            //同步保存数据
                            //smsService.recod();
                            logger.info("【redisson】 秒杀成功， 等待生成订单");
                            logger.info("【数量】: {}", num-1);
                            return Results.SUCCESS.result("秒杀成功", null);

                        }
                    } else {
                        logger.info("【redisson】 库存不足");
                        return Results.BAD__REQUEST.result("库存不足", null);
                    }
                }catch (Exception e){
                    logger.error("【exception】 msg:{}", e.getStackTrace());
                    //return Results.BAD__REQUEST.result("系统繁忙，请刷新重试", null);
                }finally {
                    lock.unlock();
                }
//            }else{
//                logger.info("【秒杀】获取锁失败");
//                return Results.BAD__REQUEST.result("系统繁忙，请刷新重试", null);
//            }
        }catch(Exception e) {
            logger.error("【秒杀】线程中断:{}", e.getStackTrace());
            //return Results.BAD__REQUEST.result("系统繁忙，请刷新重试", null);

        }
            return Results.BAD__REQUEST.result("bad request", null);
//        }else{
//            logger.error("【lock】 获取锁失败");
//            return Results.BAD__REQUEST.result("系统繁忙，请刷新重试", null);
//        }

    }


    /***
     * 普通方式队列
     * @param message
     * @return
     */
    @RequestMapping("/testQueue")
    public ResponseEntity testQueue(String message){
        RTopic topic = redissonClient.getTopic("queue", new SerializationCodec());
        topic.publish("haha, it's a test");

        return ResponseEntity.ok("success");
    }

    /****
     * 数据会先存在于延迟队列中1分钟，之后会传递消息给阻塞队列，阻塞队列就可以对其取消
     * @param message
     * @return
     */
    @RequestMapping("/testDelayQueue")
    public ResponseEntity delayQueue(String message){
        Stopwatch stopwatch = Stopwatch.createStarted();
        RBlockingQueue blockingQueue = redissonClient.getBlockingQueue("queue", new SerializationCodec());
        RDelayedQueue queue = redissonClient.getDelayedQueue(blockingQueue);
        queue.offer(message, 1, TimeUnit.MINUTES);
        System.out.println(stopwatch.elapsed(TimeUnit.MILLISECONDS));
        return ResponseEntity.ok("订单生成成功，请及时完成支付，订单将于1分钟后取消");
    }

    @RequestMapping("/testConsumeQueue")
    public ResponseEntity consumeQueue(String message){
        RBlockingQueue blockingQueue = redissonClient.getBlockingQueue("queue", new SerializationCodec());
        RDelayedQueue queue = redissonClient.getDelayedQueue(blockingQueue);
        if(!queue.contains(message)){
            System.out.println("该订单不存在");
        }
        if(queue.remove(message)){
            System.out.println("订单支付成功");
        }

        return ResponseEntity.ok("ok");
    }

}
