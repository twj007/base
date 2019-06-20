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
import org.springframework.stereotype.Component;

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

    @RabbitHandler
    @DataSource(type = "master")
    public void process(SmsFlashPromotionProductRelation product){
//        select pp.name, p.title, r.flash_promotion_price
//        from sms_flash_promotion_product_relation r left join sms_flash_promotion p on r.flash_promotion_id=p.id left join pms_product pp on r.product_id=pp.id;

        //1. 尝试扣减库存， 如果扣除成功， 生成订单消息（成功）, 扣除失败， 生成订单为失败状态
        logger.info("【订单库存处理】");
        relationMapper.saveDetail(product);
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
