package com.mall.pojo;

import java.io.Serializable;
import java.util.Date;

public class PmsCommentReplay implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column pms_comment_replay.id
     *
     * @mbggenerated
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column pms_comment_replay.comment_id
     *
     * @mbggenerated
     */
    private Long commentId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column pms_comment_replay.member_nick_name
     *
     * @mbggenerated
     */
    private String memberNickName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column pms_comment_replay.member_icon
     *
     * @mbggenerated
     */
    private String memberIcon;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column pms_comment_replay.content
     *
     * @mbggenerated
     */
    private String content;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column pms_comment_replay.create_time
     *
     * @mbggenerated
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column pms_comment_replay.type
     *
     * @mbggenerated
     */
    private Integer type;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table pms_comment_replay
     *
     * @mbggenerated
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column pms_comment_replay.id
     *
     * @return the value of pms_comment_replay.id
     *
     * @mbggenerated
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column pms_comment_replay.id
     *
     * @param id the value for pms_comment_replay.id
     *
     * @mbggenerated
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column pms_comment_replay.comment_id
     *
     * @return the value of pms_comment_replay.comment_id
     *
     * @mbggenerated
     */
    public Long getCommentId() {
        return commentId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column pms_comment_replay.comment_id
     *
     * @param commentId the value for pms_comment_replay.comment_id
     *
     * @mbggenerated
     */
    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column pms_comment_replay.member_nick_name
     *
     * @return the value of pms_comment_replay.member_nick_name
     *
     * @mbggenerated
     */
    public String getMemberNickName() {
        return memberNickName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column pms_comment_replay.member_nick_name
     *
     * @param memberNickName the value for pms_comment_replay.member_nick_name
     *
     * @mbggenerated
     */
    public void setMemberNickName(String memberNickName) {
        this.memberNickName = memberNickName == null ? null : memberNickName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column pms_comment_replay.member_icon
     *
     * @return the value of pms_comment_replay.member_icon
     *
     * @mbggenerated
     */
    public String getMemberIcon() {
        return memberIcon;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column pms_comment_replay.member_icon
     *
     * @param memberIcon the value for pms_comment_replay.member_icon
     *
     * @mbggenerated
     */
    public void setMemberIcon(String memberIcon) {
        this.memberIcon = memberIcon == null ? null : memberIcon.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column pms_comment_replay.content
     *
     * @return the value of pms_comment_replay.content
     *
     * @mbggenerated
     */
    public String getContent() {
        return content;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column pms_comment_replay.content
     *
     * @param content the value for pms_comment_replay.content
     *
     * @mbggenerated
     */
    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column pms_comment_replay.create_time
     *
     * @return the value of pms_comment_replay.create_time
     *
     * @mbggenerated
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column pms_comment_replay.create_time
     *
     * @param createTime the value for pms_comment_replay.create_time
     *
     * @mbggenerated
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column pms_comment_replay.type
     *
     * @return the value of pms_comment_replay.type
     *
     * @mbggenerated
     */
    public Integer getType() {
        return type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column pms_comment_replay.type
     *
     * @param type the value for pms_comment_replay.type
     *
     * @mbggenerated
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pms_comment_replay
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
        sb.append(", commentId=").append(commentId);
        sb.append(", memberNickName=").append(memberNickName);
        sb.append(", memberIcon=").append(memberIcon);
        sb.append(", content=").append(content);
        sb.append(", createTime=").append(createTime);
        sb.append(", type=").append(type);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}