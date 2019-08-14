package com.mall.component;


import com.mall.config.RabbitConfig;
import com.mall.dao.SmsFlashPromotionProductRelationMapper;
import com.mall.pojo.OmsOrder;
import com.mall.pojo.PmsProduct;
import com.mall.pojo.SmsFlashPromotionProductRelation;
import com.mall.service.ISmsService;
import com.mall.util.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/***
 **@project: base
 **@description:
 **@Author: twj
 **@Date: 2019/06/11
 **/
@Component
@RabbitListener(queues = RabbitConfig.QUEUE_A)
public class RabbitReceiver {

    private static final Logger logger = LoggerFactory.getLogger(RabbitReceiver.class);

    @Autowired
    private SmsFlashPromotionProductRelationMapper relationMapper;

    @Autowired
    private JavaMailSender javaMailSender;

    @RabbitHandler()
    @DataSource(type = "master")
    public void process(String target) throws MessagingException {
//        select pp.name, p.title, r.flash_promotion_price
//        from sms_flash_promotion_product_relation r left join sms_flash_promotion p on r.flash_promotion_id=p.id left join pms_product pp on r.product_id=pp.id;

        //1. 尝试扣减库存， 如果扣除成功， 生成订单消息（成功）, 扣除失败， 生成订单为失败状态
//        logger.info("【订单库存处理】");
//        relationMapper.saveDetail(product);
        MimeMessage message = javaMailSender.createMimeMessage();
        message.setSubject("Another Title of MimeMessage");
        //发件人，注意中文的处理
        message.setFrom("18779156726@163.com");
        //设置邮件回复人
//        msg.setReplyTo(new Address[]{new InternetAddress("harry.hu@derbysoft.com")});

        message.setRecipients(Message.RecipientType.TO, target);
        //抄送
        message.setRecipients(Message.RecipientType.CC, target);

        //整封邮件的MINE消息体 multipart/mixed 可添加附件 > multipart/related 内嵌资源 > multipart/alternative 超文本
        MimeMultipart msgMultipart = new MimeMultipart("alternative");//混合的组合关系
        //设置邮件的MINE消息体
        message.setContent(msgMultipart);
        // 装载附件
//        if (bytes != null && names != null) {
//            for (int i = 0; i < bytes.length; i++) {
//                MimeBodyPart attch = new MimeBodyPart(); // 附件
//                msgMultipart.addBodyPart(attch);         // 将附件添加到MIME消息体中
//                ByteArrayDataSource dataSource = new ByteArrayDataSource(bytes[i], "text/data"); //数据源
//                attch.setDataHandler(new DataHandler(dataSource));
//                attch.setFileName(names[i]);
//            }
//        }

        //html代码部分
        MimeBodyPart htmlPart = new MimeBodyPart();
        msgMultipart.addBodyPart(htmlPart);
        //html代码
        htmlPart.setContent("<p><H1>It's start</H1><p><div><strong>It's content</strong></div>", "text/html;charset=utf-8");
        logger.info("【发送邮件】: {}", message);
        javaMailSender.send(message);
//        SmsFlashPromotionProductRelation relation = relationMapper.selectOne(product);
//        relation.setFlashPromotionCount(product.getFlashPromotionCount());
//        Long num = relationMapper.updateWithPessmisvLock(relation);
//        OmsOrder omsOrder = new OmsOrder();
//        if(num == 0l){
//            omsOrder.setDeleteStatus(1);
//        }
        //创建订单记录

    }
}
