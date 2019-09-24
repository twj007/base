import com.google.common.base.Stopwatch;
import com.mall.MallApplication;
import com.mall.component.RabbitProducer;
import com.mall.pojo.SerizableMimeMessage;
import com.sun.mail.util.MimeUtil;
import jodd.util.StringUtil;
import org.junit.After;
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

import javax.activation.DataHandler;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimePart;
import javax.mail.util.ByteArrayDataSource;
import java.util.concurrent.*;
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

    @Test
    public void testSend() throws InterruptedException{
        Stopwatch stopwatch = Stopwatch.createStarted();
        CountDownLatch downLatch = new CountDownLatch(1000);
        Semaphore semaphore = new Semaphore(100);
        ExecutorService service = Executors.newCachedThreadPool();
        for(int i = 0; i < 1000; i++, downLatch.countDown()){
            service.execute(()->{
                try{
                    semaphore.acquire();
                    rabbitProducer.sendMessage(count.get());
                    count.getAndIncrement();
                }catch (InterruptedException e){
                    e.printStackTrace();
                }finally {
                    semaphore.release();
                }


            });
        }
        downLatch.await();
        System.out.println("【used time】："+ stopwatch.elapsed(TimeUnit.MILLISECONDS));
    }
}
