package com.mall.dao;

import com.mall.pojo.SmsHomeRecommendSubject;
import java.util.List;

public interface SmsHomeRecommendSubjectMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sms_home_recommend_subject
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sms_home_recommend_subject
     *
     * @mbggenerated
     */
    int insert(SmsHomeRecommendSubject record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sms_home_recommend_subject
     *
     * @mbggenerated
     */
    SmsHomeRecommendSubject selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sms_home_recommend_subject
     *
     * @mbggenerated
     */
    List<SmsHomeRecommendSubject> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sms_home_recommend_subject
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(SmsHomeRecommendSubject record);
}