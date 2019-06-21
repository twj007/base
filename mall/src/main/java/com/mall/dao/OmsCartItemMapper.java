package com.mall.dao;

import com.mall.pojo.OmsCartItem;
import java.util.List;

public interface OmsCartItemMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table oms_cart_item
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table oms_cart_item
     *
     * @mbggenerated
     */
    int insert(OmsCartItem record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table oms_cart_item
     *
     * @mbggenerated
     */
    OmsCartItem selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table oms_cart_item
     *
     * @mbggenerated
     */
    List<OmsCartItem> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table oms_cart_item
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(OmsCartItem record);
}