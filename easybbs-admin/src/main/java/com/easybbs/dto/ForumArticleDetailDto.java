package com.easybbs.dto;

import lombok.Data;

@Data
public class ForumArticleDetailDto {
    private ForumArticleDto forumArticle;
    private ForumArticleAttachmentDto attachment;
    private Boolean haveLike = false;
}
