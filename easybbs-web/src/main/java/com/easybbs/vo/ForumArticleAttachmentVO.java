package com.easybbs.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class ForumArticleAttachmentVO implements Serializable {
    private String fileId;
    private Long fileSize;
    private String fileName;
    private Integer downloadCount;
    private Integer fileType;
    private Integer integral;
}
