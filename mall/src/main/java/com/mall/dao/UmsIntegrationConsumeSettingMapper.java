package com.mall.dao;

import com.mall.pojo.UmsIntegrationConsumeSetting;
import java.util.List;

public interface UmsIntegrationConsumeSettingMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ums_integration_consume_setting
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ums_integration_consume_setting
     *
     * @mbggenerated
     */
    int insert(UmsIntegrationConsumeSetting record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ums_integration_consume_setting
     *
     * @mbggenerated
     */
    UmsIntegrationConsumeSetting selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ums_integration_consume_setting
     *
     * @mbggenerated
     */
    List<UmsIntegrationConsumeSetting> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ums_integration_consume_setting
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(UmsIntegrationConsumeSetting record);
}