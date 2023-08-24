package com.easybbs.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 文件信息
 * @TableName forum_article_attachment
 */
@TableName(value ="forum_article_attachment")
@Data
public class ForumArticleAttachment implements Serializable {
    /**
     * 文件ID
     */
    @TableId
    private String fileId;

    /**
     * 文章ID
     */
    private String articleId;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 文件大小
     */
    private Long fileSize;

    /**
     * 文件名称
     */
    private String fileName;

    /**
     * 下载次数
     */
    private Integer downloadCount;

    /**
     * 文件路径
     */
    private String filePath;

    /**
     * 文件类型
     */
    private Integer fileType;

    /**
     * 下载所需积分
     */
    private Integer integral;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}