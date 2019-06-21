package com.mall.dao;

import com.mall.pojo.UmsMemberTag;
import java.util.List;

public interface UmsMemberTagMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ums_member_tag
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ums_member_tag
     *
     * @mbggenerated
     */
    int insert(UmsMemberTag record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ums_member_tag
     *
     * @mbggenerated
     */
    UmsMemberTag selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ums_member_tag
     *
     * @mbggenerated
     */
    List<UmsMemberTag> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ums_member_tag
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(UmsMemberTag record);
}