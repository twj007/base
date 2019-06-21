package com.mall.pojo;

import java.io.Serializable;
import java.util.Date;

public class SmsFlashPromotion implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sms_flash_promotion.id
     *
     * @mbggenerated
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sms_flash_promotion.title
     *
     * @mbggenerated
     */
    private String title;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sms_flash_promotion.start_date
     *
     * @mbggenerated
     */
    private Date startDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sms_flash_promotion.end_date
     *
     * @mbggenerated
     */
    private Date endDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sms_flash_promotion.status
     *
     * @mbggenerated
     */
    private Integer status;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sms_flash_promotion.create_time
     *
     * @mbggenerated
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table sms_flash_promotion
     *
     * @mbggenerated
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sms_flash_promotion.id
     *
     * @return the value of sms_flash_promotion.id
     *
     * @mbggenerated
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sms_flash_promotion.id
     *
     * @param id the value for sms_flash_promotion.id
     *
     * @mbggenerated
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sms_flash_promotion.title
     *
     * @return the value of sms_flash_promotion.title
     *
     * @mbggenerated
     */
    public String getTitle() {
        return title;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sms_flash_promotion.title
     *
     * @param title the value for sms_flash_promotion.title
     *
     * @mbggenerated
     */
    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sms_flash_promotion.start_date
     *
     * @return the value of sms_flash_promotion.start_date
     *
     * @mbggenerated
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sms_flash_promotion.start_date
     *
     * @param startDate the value for sms_flash_promotion.start_date
     *
     * @mbggenerated
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sms_flash_promotion.end_date
     *
     * @return the value of sms_flash_promotion.end_date
     *
     * @mbggenerated
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sms_flash_promotion.end_date
     *
     * @param endDate the value for sms_flash_promotion.end_date
     *
     * @mbggenerated
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sms_flash_promotion.status
     *
     * @return the value of sms_flash_promotion.status
     *
     * @mbggenerated
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sms_flash_promotion.status
     *
     * @param status the value for sms_flash_promotion.status
     *
     * @mbggenerated
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sms_flash_promotion.create_time
     *
     * @return the value of sms_flash_promotion.create_time
     *
     * @mbggenerated
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sms_flash_promotion.create_time
     *
     * @param createTime the value for sms_flash_promotion.create_time
     *
     * @mbggenerated
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sms_flash_promotion
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
        sb.append(", title=").append(title);
        sb.append(", startDate=").append(startDate);
        sb.append(", endDate=").append(endDate);
        sb.append(", status=").append(status);
        sb.append(", createTime=").append(createTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}