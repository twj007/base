package com.mall.dao;

import com.mall.pojo.UmsMemberLoginLog;
import java.util.List;

public interface UmsMemberLoginLogMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ums_member_login_log
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ums_member_login_log
     *
     * @mbggenerated
     */
    int insert(UmsMemberLoginLog record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ums_member_login_log
     *
     * @mbggenerated
     */
    UmsMemberLoginLog selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ums_member_login_log
     *
     * @mbggenerated
     */
    List<UmsMemberLoginLog> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ums_member_login_log
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(UmsMemberLoginLog record);
}