package com.mall.dao;

import com.mall.pojo.UmsMemberRuleSetting;
import java.util.List;

public interface UmsMemberRuleSettingMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ums_member_rule_setting
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ums_member_rule_setting
     *
     * @mbggenerated
     */
    int insert(UmsMemberRuleSetting record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ums_member_rule_setting
     *
     * @mbggenerated
     */
    UmsMemberRuleSetting selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ums_member_rule_setting
     *
     * @mbggenerated
     */
    List<UmsMemberRuleSetting> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ums_member_rule_setting
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(UmsMemberRuleSetting record);
}