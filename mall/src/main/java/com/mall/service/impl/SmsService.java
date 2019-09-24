package com.mall.service.impl;

import com.mall.dao.PmsProductMapper;
import com.mall.dao.SmsFlashPromotionMapper;
import com.mall.pojo.PmsProduct;
import com.mall.pojo.SmsFlashPromotion;
import com.mall.service.ISmsService;
import com.mall.util.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/***
 **@project: base
 **@description: miaosha
 **@Author: twj
 **@Date: 2019/06/20
 **/
@Service("smsService")
public class SmsService implements ISmsService {

    @Autowired
    private SmsFlashPromotionMapper smsFlashPromotionMapper;

    @Autowired
    private PmsProductMapper pmsProductMapper;

    @Override
    public List<SmsFlashPromotion> getAllFlashActivities() {
        return smsFlashPromotionMapper.selectAll();
    }

    @Override
    public SmsFlashPromotion getFlashActivity(Long id) {
        return smsFlashPromotionMapper.selectByPrimaryKey(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public Boolean updateFlashPromotion(SmsFlashPromotion smsFlashPromotion) {
        SmsFlashPromotion promotion = smsFlashPromotionMapper.selectByPrimaryKey(smsFlashPromotion.getId());
        return false;
    }

    @Override
    public Long createFlashPromotionOrder(SmsFlashPromotion smsFlashPromotion) {
        return null;
    }

    @Override
    @DataSource
    public void recod() {
        pmsProductMapper.recod();
    }
}
