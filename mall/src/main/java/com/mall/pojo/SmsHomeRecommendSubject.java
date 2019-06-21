package com.mall.pojo;

import java.io.Serializable;

public class SmsHomeRecommendSubject implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sms_home_recommend_subject.id
     *
     * @mbggenerated
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sms_home_recommend_subject.subject_id
     *
     * @mbggenerated
     */
    private Long subjectId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sms_home_recommend_subject.subject_name
     *
     * @mbggenerated
     */
    private String subjectName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sms_home_recommend_subject.recommend_status
     *
     * @mbggenerated
     */
    private Integer recommendStatus;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sms_home_recommend_subject.sort
     *
     * @mbggenerated
     */
    private Integer sort;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table sms_home_recommend_subject
     *
     * @mbggenerated
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sms_home_recommend_subject.id
     *
     * @return the value of sms_home_recommend_subject.id
     *
     * @mbggenerated
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sms_home_recommend_subject.id
     *
     * @param id the value for sms_home_recommend_subject.id
     *
     * @mbggenerated
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sms_home_recommend_subject.subject_id
     *
     * @return the value of sms_home_recommend_subject.subject_id
     *
     * @mbggenerated
     */
    public Long getSubjectId() {
        return subjectId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sms_home_recommend_subject.subject_id
     *
     * @param subjectId the value for sms_home_recommend_subject.subject_id
     *
     * @mbggenerated
     */
    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sms_home_recommend_subject.subject_name
     *
     * @return the value of sms_home_recommend_subject.subject_name
     *
     * @mbggenerated
     */
    public String getSubjectName() {
        return subjectName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sms_home_recommend_subject.subject_name
     *
     * @param subjectName the value for sms_home_recommend_subject.subject_name
     *
     * @mbggenerated
     */
    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName == null ? null : subjectName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sms_home_recommend_subject.recommend_status
     *
     * @return the value of sms_home_recommend_subject.recommend_status
     *
     * @mbggenerated
     */
    public Integer getRecommendStatus() {
        return recommendStatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sms_home_recommend_subject.recommend_status
     *
     * @param recommendStatus the value for sms_home_recommend_subject.recommend_status
     *
     * @mbggenerated
     */
    public void setRecommendStatus(Integer recommendStatus) {
        this.recommendStatus = recommendStatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sms_home_recommend_subject.sort
     *
     * @return the value of sms_home_recommend_subject.sort
     *
     * @mbggenerated
     */
    public Integer getSort() {
        return sort;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sms_home_recommend_subject.sort
     *
     * @param sort the value for sms_home_recommend_subject.sort
     *
     * @mbggenerated
     */
    public void setSort(Integer sort) {
        this.sort = sort;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sms_home_recommend_subject
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
        sb.append(", subjectId=").append(subjectId);
        sb.append(", subjectName=").append(subjectName);
        sb.append(", recommendStatus=").append(recommendStatus);
        sb.append(", sort=").append(sort);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}