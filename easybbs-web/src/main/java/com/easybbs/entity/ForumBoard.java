package com.easybbs.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 文章板块信息
 *
 * @TableName forum_board
 */
@Data
public class ForumBoard implements Serializable {
    /**
     * 板块ID
     */
    private Long boardId;

    /**
     * 父级板块ID
     */
    private Long pBoardId;

    /**
     * 板块名
     */
    private String boardName;

    /**
     * 封面
     */
    private String cover;

    /**
     * 描述
     */
    private String boardDesc;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 0:只允许管理员发帖 1:任何人可以发帖
     */
    private Integer postType;

    private static final long serialVersionUID = 1L;
}