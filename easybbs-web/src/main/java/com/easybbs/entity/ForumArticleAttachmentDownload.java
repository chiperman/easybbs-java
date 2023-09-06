package com.easybbs.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 用户附件下载
 * @TableName forum_article_attachment_download
 */
@TableName(value ="forum_article_attachment_download")
@Data
public class ForumArticleAttachmentDownload implements Serializable {
    /**
     * 文件ID
     */
    @TableId
    private String fileId;

    /**
     * 用户id
     */
    @TableId
    private String userId;

    /**
     * 文章ID
     */
    private String articleId;

    /**
     * 文件下载次数
     */
    private Integer downloadCount;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}