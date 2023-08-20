package com.easybbs.entity;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 文章信息
 * @TableName forum_article
 */
@Data
public class ForumArticle implements Serializable {
    /**
     * 文章ID
     */
    private String articleId;

    /**
     * 板块ID
     */
    private Integer boardId;

    /**
     * 板块名称
     */
    private String boardName;

    /**
     * 父级板块ID
     */
    private Integer pBoardId;

    /**
     * 父板块名称
     */
    private String pBoardName;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 最后登录ip地址
     */
    private String userIpAddress;

    /**
     * 标题
     */
    private String title;

    /**
     * 封面
     */
    private String cover;

    /**
     * 内容
     */
    private String content;

    /**
     * markdown内容
     */
    private String markdownContent;

    /**
     * 0:富文本编辑器 1:markdown编辑器
     */
    private Integer editorType;

    /**
     * 摘要
     */
    private String summary;

    /**
     * 发布时间
     */
    private Date postTime;

    /**
     * 最后更新时间
     */
    private Date lastUpdateTime;

    /**
     * 阅读数量
     */
    private Integer readCount;

    /**
     * 点赞数
     */
    private Integer goodCount;

    /**
     * 评论数
     */
    private Integer commentCount;

    /**
     * 0未置顶  1:已置顶
     */
    private Integer topType;

    /**
     * 0:没有附件  1:有附件
     */
    private Integer attachmentType;

    /**
     * -1已删除 0:待审核  1:已审核 
     */
    private Integer status;

    private static final long serialVersionUID = 1L;
}