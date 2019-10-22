
import com.google.common.base.Stopwatch;
import com.mall.MallApplication;
import com.mall.component.RabbitProducer;
import com.mall.dao.SmsFlashPromotionProductRelationMapper;
import com.mall.pojo.SmsFlashPromotionProductRelation;
import javafx.scene.paint.Stop;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.StopWatch;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/***
 **@project: base
 **@description:
 **@Author: twj
 **@Date: 2019/08/14
 **/
@SpringBootTest(classes = MallApplication.class)
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class TestMail {

    @Before
    public void before(){}

    @After
    public void after(){}


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RabbitProducer rabbitProducer;

    @Autowired
    private JavaMailSender javaMailSender;



//    public void testMail() throws MessagingException {
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setFrom("18779156726@163.com");
//        message.setTo("530747628@qq.com");
//        message.setSubject("Test title");
//        message.setText("only test if it's working");
//        javaMailSender.send(message);
        //自定义一个接收对象
//        String target = "530747628@qq.com";
//        rabbitProducer.sendEmail(target);
//    }

    private volatile AtomicInteger count = new AtomicInteger(0);

    @Autowired
    private SmsFlashPromotionProductRelationMapper relationMapper;

//    @Test
//    public void testOrder(){
//        Stopwatch stopWatch = Stopwatch.createStarted();
//        SmsFlashPromotionProductRelation r = new SmsFlashPromotionProductRelation();
//        r.setUserId(123L);
//        r.setProductId(4L);
//        int count = relationMapper.saveOrder(r);
//        Assert.assertNotEquals(0, count);
//
//    }
    @Test
    public void testUpdateOrder(){
        Stopwatch stopWatch = Stopwatch.createStarted();
        SmsFlashPromotionProductRelation r = new SmsFlashPromotionProductRelation();
        r.setUserId(7806L);
        r.setProductId(4L);
        r.setStatus("3");
        int count = relationMapper.cancelOrder(r);
        Assert.assertNotEquals(0, count);
        SmsFlashPromotionProductRelation r2 = new SmsFlashPromotionProductRelation();
        r2.setId(5411L);
        r2.setStatus("3");
        int count2 = relationMapper.cancelOrder(r2);
        Assert.assertNotEquals(0, count2);
    }

//    @Test
//    public void testSelectOrder(){
//        Stopwatch stopwatch = Stopwatch.createStarted();
//        SmsFlashPromotionProductRelation r = new SmsFlashPromotionProductRelation();
//        r.setId(5409L);
//        r = relationMapper.getOrder(r);
//        System.out.println(r);
//        Assert.assertSame(4l, r.getProductId());
//        Assert.assertSame( 7806l, r.getUserId());
//        System.out.println(stopwatch.elapsed(TimeUnit.MILLISECONDS));
//    }

//    @Test
//    public void testCancelOrder(){
//        Stopwatch stopwatch = Stopwatch.createStarted();
//        SmsFlashPromotionProductRelation r = new SmsFlashPromotionProductRelation();
//        r.setProductId(4L);
//        r.setUserId(6964L);
//        int num = relationMapper.cancelOrder(r);
//        Assert.assertNotEquals(0L, num);
//    }

//    @Test
//    public void testSend() throws InterruptedException{
//        Stopwatch stopwatch = Stopwatch.createStarted();
//        CountDownLatch downLatch = new CountDownLatch(1000);
//        Semaphore semaphore = new Semaphore(100);
//        ExecutorService service = Executors.newCachedThreadPool();
//        for(int i = 0; i < 1000; i++, downLatch.countDown()){
//            service.execute(()->{
//                try{
//                    semaphore.acquire();
//                    rabbitProducer.sendMessage(count.get());
//                    count.getAndIncrement();
//                }catch (InterruptedException e){
//                    e.printStackTrace();
//                }finally {
//                    semaphore.release();
//                }
//
//
//            });
//        }
//        downLatch.await();
//        System.out.println("【used time】："+ stopwatch.elapsed(TimeUnit.MILLISECONDS));
//    }
}
