package com.mall.dao;

import com.mall.pojo.UmsIntegrationChangeHistory;
import java.util.List;

public interface UmsIntegrationChangeHistoryMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ums_integration_change_history
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ums_integration_change_history
     *
     * @mbggenerated
     */
    int insert(UmsIntegrationChangeHistory record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ums_integration_change_history
     *
     * @mbggenerated
     */
    UmsIntegrationChangeHistory selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ums_integration_change_history
     *
     * @mbggenerated
     */
    List<UmsIntegrationChangeHistory> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ums_integration_change_history
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(UmsIntegrationChangeHistory record);
}