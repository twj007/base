package com.mall.dao;

import com.mall.pojo.CmsHelp;
import java.util.List;

public interface CmsHelpMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table cms_help
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table cms_help
     *
     * @mbggenerated
     */
    int insert(CmsHelp record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table cms_help
     *
     * @mbggenerated
     */
    CmsHelp selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table cms_help
     *
     * @mbggenerated
     */
    List<CmsHelp> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table cms_help
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(CmsHelp record);
}