package com.mall.service.impl;

import com.mall.dao.PmsProductMapper;
import com.mall.dao.SmsFlashPromotionMapper;
import com.mall.dao.SmsFlashPromotionProductRelationMapper;
import com.mall.pojo.SmsFlashPromotion;
import com.mall.pojo.SmsFlashPromotionProductRelation;
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

    @Autowired
    private SmsFlashPromotionProductRelationMapper relation;

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

    @Override
    @DataSource
    public int cancelOrder(SmsFlashPromotionProductRelation message) {
        return relation.cancelOrder(message);
    }

    @Override
    public void saveOrder(SmsFlashPromotionProductRelation product) {
        relation.saveOrder(product);
    }

    public SmsFlashPromotionProductRelation getOrder(SmsFlashPromotionProductRelation productRelation) {
        return relation.getOrder(productRelation);
    }
}
