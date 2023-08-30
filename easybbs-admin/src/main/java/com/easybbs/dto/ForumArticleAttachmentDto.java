package com.easybbs.dto;

import lombok.Data;

@Data
public class ForumArticleAttachmentDto {
    private String fileId;
    private Long fileSize;
    private String fileName;
    private Integer downloadCount;
    private Integer fileType;
    private Integer integral;
}
