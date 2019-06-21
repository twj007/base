package com.mall.pojo;

import java.io.Serializable;
import java.math.BigDecimal;

public class SmsFlashPromotionProductRelation implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sms_flash_promotion_product_relation.id
     *
     * @mbggenerated
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sms_flash_promotion_product_relation.flash_promotion_id
     *
     * @mbggenerated
     */
    private Long flashPromotionId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sms_flash_promotion_product_relation.flash_promotion_session_id
     *
     * @mbggenerated
     */
    private Long flashPromotionSessionId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sms_flash_promotion_product_relation.product_id
     *
     * @mbggenerated
     */
    private Long productId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sms_flash_promotion_product_relation.flash_promotion_price
     *
     * @mbggenerated
     */
    private BigDecimal flashPromotionPrice;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sms_flash_promotion_product_relation.flash_promotion_count
     *
     * @mbggenerated
     */
    private Integer flashPromotionCount;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sms_flash_promotion_product_relation.flash_promotion_limit
     *
     * @mbggenerated
     */
    private Integer flashPromotionLimit;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sms_flash_promotion_product_relation.sort
     *
     * @mbggenerated
     */
    private Integer sort;

    private Long userId;


    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table sms_flash_promotion_product_relation
     *
     * @mbggenerated
     */
    private static final long serialVersionUID = 1L;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sms_flash_promotion_product_relation.id
     *
     * @return the value of sms_flash_promotion_product_relation.id
     *
     * @mbggenerated
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sms_flash_promotion_product_relation.id
     *
     * @param id the value for sms_flash_promotion_product_relation.id
     *
     * @mbggenerated
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sms_flash_promotion_product_relation.flash_promotion_id
     *
     * @return the value of sms_flash_promotion_product_relation.flash_promotion_id
     *
     * @mbggenerated
     */
    public Long getFlashPromotionId() {
        return flashPromotionId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sms_flash_promotion_product_relation.flash_promotion_id
     *
     * @param flashPromotionId the value for sms_flash_promotion_product_relation.flash_promotion_id
     *
     * @mbggenerated
     */
    public void setFlashPromotionId(Long flashPromotionId) {
        this.flashPromotionId = flashPromotionId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sms_flash_promotion_product_relation.flash_promotion_session_id
     *
     * @return the value of sms_flash_promotion_product_relation.flash_promotion_session_id
     *
     * @mbggenerated
     */
    public Long getFlashPromotionSessionId() {
        return flashPromotionSessionId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sms_flash_promotion_product_relation.flash_promotion_session_id
     *
     * @param flashPromotionSessionId the value for sms_flash_promotion_product_relation.flash_promotion_session_id
     *
     * @mbggenerated
     */
    public void setFlashPromotionSessionId(Long flashPromotionSessionId) {
        this.flashPromotionSessionId = flashPromotionSessionId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sms_flash_promotion_product_relation.product_id
     *
     * @return the value of sms_flash_promotion_product_relation.product_id
     *
     * @mbggenerated
     */
    public Long getProductId() {
        return productId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sms_flash_promotion_product_relation.product_id
     *
     * @param productId the value for sms_flash_promotion_product_relation.product_id
     *
     * @mbggenerated
     */
    public void setProductId(Long productId) {
        this.productId = productId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sms_flash_promotion_product_relation.flash_promotion_price
     *
     * @return the value of sms_flash_promotion_product_relation.flash_promotion_price
     *
     * @mbggenerated
     */
    public BigDecimal getFlashPromotionPrice() {
        return flashPromotionPrice;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sms_flash_promotion_product_relation.flash_promotion_price
     *
     * @param flashPromotionPrice the value for sms_flash_promotion_product_relation.flash_promotion_price
     *
     * @mbggenerated
     */
    public void setFlashPromotionPrice(BigDecimal flashPromotionPrice) {
        this.flashPromotionPrice = flashPromotionPrice;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sms_flash_promotion_product_relation.flash_promotion_count
     *
     * @return the value of sms_flash_promotion_product_relation.flash_promotion_count
     *
     * @mbggenerated
     */
    public Integer getFlashPromotionCount() {
        return flashPromotionCount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sms_flash_promotion_product_relation.flash_promotion_count
     *
     * @param flashPromotionCount the value for sms_flash_promotion_product_relation.flash_promotion_count
     *
     * @mbggenerated
     */
    public void setFlashPromotionCount(Integer flashPromotionCount) {
        this.flashPromotionCount = flashPromotionCount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sms_flash_promotion_product_relation.flash_promotion_limit
     *
     * @return the value of sms_flash_promotion_product_relation.flash_promotion_limit
     *
     * @mbggenerated
     */
    public Integer getFlashPromotionLimit() {
        return flashPromotionLimit;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sms_flash_promotion_product_relation.flash_promotion_limit
     *
     * @param flashPromotionLimit the value for sms_flash_promotion_product_relation.flash_promotion_limit
     *
     * @mbggenerated
     */
    public void setFlashPromotionLimit(Integer flashPromotionLimit) {
        this.flashPromotionLimit = flashPromotionLimit;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sms_flash_promotion_product_relation.sort
     *
     * @return the value of sms_flash_promotion_product_relation.sort
     *
     * @mbggenerated
     */
    public Integer getSort() {
        return sort;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sms_flash_promotion_product_relation.sort
     *
     * @param sort the value for sms_flash_promotion_product_relation.sort
     *
     * @mbggenerated
     */
    public void setSort(Integer sort) {
        this.sort = sort;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sms_flash_promotion_product_relation
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
        sb.append(", flashPromotionId=").append(flashPromotionId);
        sb.append(", flashPromotionSessionId=").append(flashPromotionSessionId);
        sb.append(", productId=").append(productId);
        sb.append(", flashPromotionPrice=").append(flashPromotionPrice);
        sb.append(", flashPromotionCount=").append(flashPromotionCount);
        sb.append(", flashPromotionLimit=").append(flashPromotionLimit);
        sb.append(", sort=").append(sort);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}