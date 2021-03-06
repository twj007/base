import com.mall.MallApplication;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.net.URI;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/***
 **@project: base
 **@description:
 **@Author: twj
 **@Date: 2019/06/20
 **/
@SpringBootTest(classes = MallApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
@AutoConfigureMockMvc
public class TestMiaosha {

    private static final Logger logger = LoggerFactory.getLogger(TestMiaosha.class);


    @Autowired
    MockMvc mockMvc;

    @Before
    public void before(){}

    @After
    public void after(){}

    /** 4.7GB - 2.7GB 24339ms 2GB  2000 / 200 10mb/t 28000/5000 4.16ms/1r
     * 并行测试
     * 28089ms 52ms 100 * 200 20000
     * 200线程 200请求 4679ms  23.45ms/request
     *  1000请求 1000并发量 15s 150ms/r
     *  10000请求 1000并发 13s  1.2ms/r 实际上只处理了1000个，其余的没有获取到线程
     * 10000请求 10000并发量 2min 12ms/r
     */
    @Test
    public void concurrency() throws InterruptedException {
        Long start = System.currentTimeMillis();
        // 请求的总数
        int clientTotal = 10000;
        // 同时并发执行的线程数
        int threadTotal = 1000;
        AtomicInteger count = new AtomicInteger(0);

        ExecutorService executorService = Executors.newCachedThreadPool();
        final Semaphore semaphore = new Semaphore(threadTotal);
        final CountDownLatch countDownLatch = new CountDownLatch(clientTotal);
        for (int i = 0; i < clientTotal ; i++) {
            executorService.execute(() -> {
                try {
                    if(semaphore.tryAcquire(1, 1, TimeUnit.SECONDS)) {
                        count.getAndIncrement();
                        logger.info("创建线程:" + Thread.currentThread().getName());
                        String result = mockMvc.perform(
                                MockMvcRequestBuilders
                                        .get(URI.create("/mall/flashPromotion?productId=4&userId=" + new Double(Math.ceil(Math.random() * 1000)).intValue()))
                                        .header("token", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzdWJqZWN0IiwiYXVkIjoiIiwiaXNzIjoidHdqIiwidXNlckFyciI6WzEsMiwzXSwiZXhwIjoxNTY1NTk4ODAwLCJpYXQiOjE1NjU1OTI4MDAsImp0aSI6IiJ9.9gGi4hnx6tV6UAHKBHseUjJ85bBvmqRMZ-ke39oJ0fc")
                        ).andExpect(MockMvcResultMatchers.status().isOk())
                                .andReturn().getResponse().getContentAsString();
                        semaphore.release();
                    }else{
                        logger.info("创建失败了");
                    }
                } catch (Exception e) {
                    logger.error("exception", e);
                }
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
        executorService.shutdown();
        // 所有线程执行完，之后才能执行的部分
        Long end = System.currentTimeMillis();
        logger.info(count.get() + "【用时】：{}ms", end-start);
    }



    /***
     * 起1000个线程进行秒杀
     * @throws Exception
     */
    @Test
    public void testRedis() throws Exception {
        Long start = System.currentTimeMillis();
        ExecutorService service = Executors.newFixedThreadPool(1000);
        for(int i = 1; i < 1000; i++) {
            service.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        String result = mockMvc.perform(
                                MockMvcRequestBuilders
                                        .get(URI.create("/flashPromotion?productId=2&userId="+new Double(Math.ceil(Math.random()*100)).intValue()))
                        ).andExpect(MockMvcResultMatchers.status().isOk())
                                .andReturn().getResponse().getContentAsString();
                        System.out.println(result);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        service.shutdown();
        Thread.currentThread().join();
        Long end = System.currentTimeMillis();
        logger.error("【用时】：{}s", end-start);



    }

}
