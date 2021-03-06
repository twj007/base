package com.mall.pojo;

import java.io.Serializable;

public class SmsCouponProductCategoryRelation implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sms_coupon_product_category_relation.id
     *
     * @mbggenerated
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sms_coupon_product_category_relation.coupon_id
     *
     * @mbggenerated
     */
    private Long couponId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sms_coupon_product_category_relation.product_category_id
     *
     * @mbggenerated
     */
    private Long productCategoryId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sms_coupon_product_category_relation.product_category_name
     *
     * @mbggenerated
     */
    private String productCategoryName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sms_coupon_product_category_relation.parent_category_name
     *
     * @mbggenerated
     */
    private String parentCategoryName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table sms_coupon_product_category_relation
     *
     * @mbggenerated
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sms_coupon_product_category_relation.id
     *
     * @return the value of sms_coupon_product_category_relation.id
     *
     * @mbggenerated
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sms_coupon_product_category_relation.id
     *
     * @param id the value for sms_coupon_product_category_relation.id
     *
     * @mbggenerated
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sms_coupon_product_category_relation.coupon_id
     *
     * @return the value of sms_coupon_product_category_relation.coupon_id
     *
     * @mbggenerated
     */
    public Long getCouponId() {
        return couponId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sms_coupon_product_category_relation.coupon_id
     *
     * @param couponId the value for sms_coupon_product_category_relation.coupon_id
     *
     * @mbggenerated
     */
    public void setCouponId(Long couponId) {
        this.couponId = couponId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sms_coupon_product_category_relation.product_category_id
     *
     * @return the value of sms_coupon_product_category_relation.product_category_id
     *
     * @mbggenerated
     */
    public Long getProductCategoryId() {
        return productCategoryId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sms_coupon_product_category_relation.product_category_id
     *
     * @param productCategoryId the value for sms_coupon_product_category_relation.product_category_id
     *
     * @mbggenerated
     */
    public void setProductCategoryId(Long productCategoryId) {
        this.productCategoryId = productCategoryId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sms_coupon_product_category_relation.product_category_name
     *
     * @return the value of sms_coupon_product_category_relation.product_category_name
     *
     * @mbggenerated
     */
    public String getProductCategoryName() {
        return productCategoryName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sms_coupon_product_category_relation.product_category_name
     *
     * @param productCategoryName the value for sms_coupon_product_category_relation.product_category_name
     *
     * @mbggenerated
     */
    public void setProductCategoryName(String productCategoryName) {
        this.productCategoryName = productCategoryName == null ? null : productCategoryName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sms_coupon_product_category_relation.parent_category_name
     *
     * @return the value of sms_coupon_product_category_relation.parent_category_name
     *
     * @mbggenerated
     */
    public String getParentCategoryName() {
        return parentCategoryName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sms_coupon_product_category_relation.parent_category_name
     *
     * @param parentCategoryName the value for sms_coupon_product_category_relation.parent_category_name
     *
     * @mbggenerated
     */
    public void setParentCategoryName(String parentCategoryName) {
        this.parentCategoryName = parentCategoryName == null ? null : parentCategoryName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sms_coupon_product_category_relation
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
        sb.append(", couponId=").append(couponId);
        sb.append(", productCategoryId=").append(productCategoryId);
        sb.append(", productCategoryName=").append(productCategoryName);
        sb.append(", parentCategoryName=").append(parentCategoryName);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}