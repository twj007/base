package com.mall.dao;

import com.mall.pojo.SmsCouponProductRelation;
import java.util.List;

public interface SmsCouponProductRelationMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sms_coupon_product_relation
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sms_coupon_product_relation
     *
     * @mbggenerated
     */
    int insert(SmsCouponProductRelation record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sms_coupon_product_relation
     *
     * @mbggenerated
     */
    SmsCouponProductRelation selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sms_coupon_product_relation
     *
     * @mbggenerated
     */
    List<SmsCouponProductRelation> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sms_coupon_product_relation
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(SmsCouponProductRelation record);
}