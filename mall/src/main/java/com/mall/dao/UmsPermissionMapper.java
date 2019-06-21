package com.mall.dao;

import com.mall.pojo.UmsPermission;
import java.util.List;

public interface UmsPermissionMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ums_permission
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ums_permission
     *
     * @mbggenerated
     */
    int insert(UmsPermission record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ums_permission
     *
     * @mbggenerated
     */
    UmsPermission selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ums_permission
     *
     * @mbggenerated
     */
    List<UmsPermission> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ums_permission
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(UmsPermission record);
}