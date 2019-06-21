package com.mall.pojo;

import java.io.Serializable;

public class SmsHomeBrand implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sms_home_brand.id
     *
     * @mbggenerated
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sms_home_brand.brand_id
     *
     * @mbggenerated
     */
    private Long brandId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sms_home_brand.brand_name
     *
     * @mbggenerated
     */
    private String brandName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sms_home_brand.recommend_status
     *
     * @mbggenerated
     */
    private Integer recommendStatus;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sms_home_brand.sort
     *
     * @mbggenerated
     */
    private Integer sort;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table sms_home_brand
     *
     * @mbggenerated
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sms_home_brand.id
     *
     * @return the value of sms_home_brand.id
     *
     * @mbggenerated
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sms_home_brand.id
     *
     * @param id the value for sms_home_brand.id
     *
     * @mbggenerated
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sms_home_brand.brand_id
     *
     * @return the value of sms_home_brand.brand_id
     *
     * @mbggenerated
     */
    public Long getBrandId() {
        return brandId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sms_home_brand.brand_id
     *
     * @param brandId the value for sms_home_brand.brand_id
     *
     * @mbggenerated
     */
    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sms_home_brand.brand_name
     *
     * @return the value of sms_home_brand.brand_name
     *
     * @mbggenerated
     */
    public String getBrandName() {
        return brandName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sms_home_brand.brand_name
     *
     * @param brandName the value for sms_home_brand.brand_name
     *
     * @mbggenerated
     */
    public void setBrandName(String brandName) {
        this.brandName = brandName == null ? null : brandName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sms_home_brand.recommend_status
     *
     * @return the value of sms_home_brand.recommend_status
     *
     * @mbggenerated
     */
    public Integer getRecommendStatus() {
        return recommendStatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sms_home_brand.recommend_status
     *
     * @param recommendStatus the value for sms_home_brand.recommend_status
     *
     * @mbggenerated
     */
    public void setRecommendStatus(Integer recommendStatus) {
        this.recommendStatus = recommendStatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sms_home_brand.sort
     *
     * @return the value of sms_home_brand.sort
     *
     * @mbggenerated
     */
    public Integer getSort() {
        return sort;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sms_home_brand.sort
     *
     * @param sort the value for sms_home_brand.sort
     *
     * @mbggenerated
     */
    public void setSort(Integer sort) {
        this.sort = sort;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sms_home_brand
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
        sb.append(", brandId=").append(brandId);
        sb.append(", brandName=").append(brandName);
        sb.append(", recommendStatus=").append(recommendStatus);
        sb.append(", sort=").append(sort);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}