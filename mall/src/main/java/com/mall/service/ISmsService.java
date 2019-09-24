package com.mall.service;

import com.mall.pojo.PmsProduct;
import com.mall.pojo.SmsFlashPromotion;

import java.util.List;

public interface ISmsService {

    List<SmsFlashPromotion> getAllFlashActivities();

    SmsFlashPromotion getFlashActivity(Long id);

    Boolean updateFlashPromotion(SmsFlashPromotion product);

    Long createFlashPromotionOrder(SmsFlashPromotion product);

    void recod();
}
