package com.mall.dao;

import com.mall.pojo.PmsCommentReplay;
import java.util.List;

public interface PmsCommentReplayMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pms_comment_replay
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pms_comment_replay
     *
     * @mbggenerated
     */
    int insert(PmsCommentReplay record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pms_comment_replay
     *
     * @mbggenerated
     */
    PmsCommentReplay selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pms_comment_replay
     *
     * @mbggenerated
     */
    List<PmsCommentReplay> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pms_comment_replay
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(PmsCommentReplay record);
}