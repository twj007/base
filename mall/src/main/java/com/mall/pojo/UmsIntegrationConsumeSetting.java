package com.mall.pojo;

import java.io.Serializable;

public class UmsIntegrationConsumeSetting implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ums_integration_consume_setting.id
     *
     * @mbggenerated
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ums_integration_consume_setting.deduction_per_amount
     *
     * @mbggenerated
     */
    private Integer deductionPerAmount;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ums_integration_consume_setting.max_percent_per_order
     *
     * @mbggenerated
     */
    private Integer maxPercentPerOrder;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ums_integration_consume_setting.use_unit
     *
     * @mbggenerated
     */
    private Integer useUnit;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ums_integration_consume_setting.coupon_status
     *
     * @mbggenerated
     */
    private Integer couponStatus;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table ums_integration_consume_setting
     *
     * @mbggenerated
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ums_integration_consume_setting.id
     *
     * @return the value of ums_integration_consume_setting.id
     *
     * @mbggenerated
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ums_integration_consume_setting.id
     *
     * @param id the value for ums_integration_consume_setting.id
     *
     * @mbggenerated
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ums_integration_consume_setting.deduction_per_amount
     *
     * @return the value of ums_integration_consume_setting.deduction_per_amount
     *
     * @mbggenerated
     */
    public Integer getDeductionPerAmount() {
        return deductionPerAmount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ums_integration_consume_setting.deduction_per_amount
     *
     * @param deductionPerAmount the value for ums_integration_consume_setting.deduction_per_amount
     *
     * @mbggenerated
     */
    public void setDeductionPerAmount(Integer deductionPerAmount) {
        this.deductionPerAmount = deductionPerAmount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ums_integration_consume_setting.max_percent_per_order
     *
     * @return the value of ums_integration_consume_setting.max_percent_per_order
     *
     * @mbggenerated
     */
    public Integer getMaxPercentPerOrder() {
        return maxPercentPerOrder;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ums_integration_consume_setting.max_percent_per_order
     *
     * @param maxPercentPerOrder the value for ums_integration_consume_setting.max_percent_per_order
     *
     * @mbggenerated
     */
    public void setMaxPercentPerOrder(Integer maxPercentPerOrder) {
        this.maxPercentPerOrder = maxPercentPerOrder;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ums_integration_consume_setting.use_unit
     *
     * @return the value of ums_integration_consume_setting.use_unit
     *
     * @mbggenerated
     */
    public Integer getUseUnit() {
        return useUnit;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ums_integration_consume_setting.use_unit
     *
     * @param useUnit the value for ums_integration_consume_setting.use_unit
     *
     * @mbggenerated
     */
    public void setUseUnit(Integer useUnit) {
        this.useUnit = useUnit;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ums_integration_consume_setting.coupon_status
     *
     * @return the value of ums_integration_consume_setting.coupon_status
     *
     * @mbggenerated
     */
    public Integer getCouponStatus() {
        return couponStatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ums_integration_consume_setting.coupon_status
     *
     * @param couponStatus the value for ums_integration_consume_setting.coupon_status
     *
     * @mbggenerated
     */
    public void setCouponStatus(Integer couponStatus) {
        this.couponStatus = couponStatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ums_integration_consume_setting
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
        sb.append(", deductionPerAmount=").append(deductionPerAmount);
        sb.append(", maxPercentPerOrder=").append(maxPercentPerOrder);
        sb.append(", useUnit=").append(useUnit);
        sb.append(", couponStatus=").append(couponStatus);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}