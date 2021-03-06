package com.mall.dao;

import com.mall.pojo.PmsSkuStock;
import java.util.List;

public interface PmsSkuStockMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pms_sku_stock
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pms_sku_stock
     *
     * @mbggenerated
     */
    int insert(PmsSkuStock record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pms_sku_stock
     *
     * @mbggenerated
     */
    PmsSkuStock selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pms_sku_stock
     *
     * @mbggenerated
     */
    List<PmsSkuStock> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pms_sku_stock
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(PmsSkuStock record);
}