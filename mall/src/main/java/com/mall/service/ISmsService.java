package com.mall.service;

import com.mall.pojo.SmsFlashPromotion;
import com.mall.pojo.SmsFlashPromotionProductRelation;

import java.util.List;

public interface ISmsService {

    List<SmsFlashPromotion> getAllFlashActivities();

    SmsFlashPromotion getFlashActivity(Long id);

    Boolean updateFlashPromotion(SmsFlashPromotion product);

    Long createFlashPromotionOrder(SmsFlashPromotion product);

    void recod();

    int cancelOrder(SmsFlashPromotionProductRelation message);

    void saveOrder(SmsFlashPromotionProductRelation product);

    SmsFlashPromotionProductRelation getOrder(SmsFlashPromotionProductRelation product);
}
