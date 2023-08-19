package com.easybbs.vo;

import lombok.Data;

import java.util.List;

@Data
public class ForumBoardResponseVO {
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

    private List<ForumBoardResponseVO> children; // 新增的属性

}
