package com.mall.pojo;

import java.io.Serializable;
import java.math.BigDecimal;

public class PmsProductFullReduction implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column pms_product_full_reduction.id
     *
     * @mbggenerated
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column pms_product_full_reduction.product_id
     *
     * @mbggenerated
     */
    private Long productId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column pms_product_full_reduction.full_price
     *
     * @mbggenerated
     */
    private BigDecimal fullPrice;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column pms_product_full_reduction.reduce_price
     *
     * @mbggenerated
     */
    private BigDecimal reducePrice;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table pms_product_full_reduction
     *
     * @mbggenerated
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column pms_product_full_reduction.id
     *
     * @return the value of pms_product_full_reduction.id
     *
     * @mbggenerated
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column pms_product_full_reduction.id
     *
     * @param id the value for pms_product_full_reduction.id
     *
     * @mbggenerated
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column pms_product_full_reduction.product_id
     *
     * @return the value of pms_product_full_reduction.product_id
     *
     * @mbggenerated
     */
    public Long getProductId() {
        return productId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column pms_product_full_reduction.product_id
     *
     * @param productId the value for pms_product_full_reduction.product_id
     *
     * @mbggenerated
     */
    public void setProductId(Long productId) {
        this.productId = productId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column pms_product_full_reduction.full_price
     *
     * @return the value of pms_product_full_reduction.full_price
     *
     * @mbggenerated
     */
    public BigDecimal getFullPrice() {
        return fullPrice;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column pms_product_full_reduction.full_price
     *
     * @param fullPrice the value for pms_product_full_reduction.full_price
     *
     * @mbggenerated
     */
    public void setFullPrice(BigDecimal fullPrice) {
        this.fullPrice = fullPrice;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column pms_product_full_reduction.reduce_price
     *
     * @return the value of pms_product_full_reduction.reduce_price
     *
     * @mbggenerated
     */
    public BigDecimal getReducePrice() {
        return reducePrice;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column pms_product_full_reduction.reduce_price
     *
     * @param reducePrice the value for pms_product_full_reduction.reduce_price
     *
     * @mbggenerated
     */
    public void setReducePrice(BigDecimal reducePrice) {
        this.reducePrice = reducePrice;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pms_product_full_reduction
     *
     * @mbggenerated
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", productId=").append(productId);
        sb.append(", fullPrice=").append(fullPrice);
        sb.append(", reducePrice=").append(reducePrice);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}