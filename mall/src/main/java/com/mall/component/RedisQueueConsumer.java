package com.mall.component;

import com.google.common.base.Stopwatch;
import com.mall.pojo.SmsFlashPromotionProductRelation;
import com.mall.service.impl.SmsService;
import org.redisson.api.*;
import org.redisson.codec.SerializationCodec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.Schedules;
import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/***
 **@project: base
 **@description:
 **@Author: twj
 **@Date: 2019/10/15
 **/
@Service
public class RedisQueueConsumer {

    private static final Logger logger = LoggerFactory.getLogger(RedisQueueConsumer.class);

    @Autowired
    private RedissonClient client;

    @Autowired
    private SmsService smsService;

    @Scheduled(cron = "0/10 * * * * ?")
    public void run(){
        BlockingQueue blockingQueue = client.getBlockingQueue("order_delay_queue", new SerializationCodec());
        logger.info("{} 定时任务开启", Thread.currentThread().getName() );
        Stopwatch stopwatch = Stopwatch.createStarted();
        if(blockingQueue == null){
            logger.info("{},  定时任务结束，执行用时：{}", Thread.currentThread().getName(), stopwatch.elapsed(TimeUnit.MILLISECONDS));
            return;
        }
        try {
            //获取15分钟没有被消费的订单，并取消，返还被扣除的库存
            //System.out.println("延迟队列执行线程");
            long message = (long) blockingQueue.poll(1, TimeUnit.SECONDS);
            if(message != 0L){
                SmsFlashPromotionProductRelation productRelation = new SmsFlashPromotionProductRelation();
                productRelation.setId(message);
                productRelation = smsService.getOrder(productRelation);
                RBucket<Integer> bucket = client.getBucket(String.valueOf(productRelation.getProductId()));
                Integer num = bucket.get();
                bucket.set(num + 1);
                productRelation.setStatus("4");
                smsService.cancelOrder(productRelation);
                logger.info("取消订单: {}", message);
            }else{
                logger.info("队列为空，等待下次执行");
            }

        } catch (InterruptedException e) {
            logger.error("[exception] : {}", e.getMessage());
        } catch (Exception e) {
            logger.info("[delay queue] 延时队列未获取需要的消息，完成定时job");
            logger.error("[exception] : {}", e.getMessage());
        }
        logger.info("[schedule]{},  定时任务结束，执行用时：{}", Thread.currentThread().getName(), stopwatch.elapsed(TimeUnit.MILLISECONDS));
    }

    //private Thread consumerThread;
    //使用线程的方式去轮询太吃cpu开销，改用job的方式去定时查询

//    @PostConstruct
//    public void init(){
//        consumerThread = new Thread(()->{
//            RBlockingQueue blockingQueue = client.getBlockingQueue("order_delay_queue", new SerializationCodec());
//            System.out.println(Thread.currentThread().getName());
//            RLock lock = client.getLock("miaosha");
//            while(true) {
//                try {
//                    //获取15分钟没有被消费的订单，并取消，返还被扣除的库存
//                    System.out.println("延迟队列执行线程");
//                    long message = (Long)blockingQueue.take();
//                    SmsFlashPromotionProductRelation productRelation = new SmsFlashPromotionProductRelation();
//                    productRelation.setId(message);
//                    productRelation = smsService.getOrder(productRelation);
//                    lock.lock(10, TimeUnit.SECONDS);
//                    RBucket<Integer> bucket = client.getBucket(String.valueOf(productRelation.getProductId()));
//                    Integer num = bucket.get();
//                    bucket.set(num + 1);
//                    productRelation.setStatus("4");
//                    smsService.cancelOrder(productRelation);
//
//                    System.out.println("取消订单" + message);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                } finally{
//                    lock.unlock();
//                }
//            }
//        });
//        consumerThread.start();
//    }
}
