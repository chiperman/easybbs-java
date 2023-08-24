package com.easybbs.vo;

import lombok.Data;

@Data
public class ForumArticleDetailVO {
    private ForumArticleVO forumArticleVO;
    private ForumArticleAttachmentVO forumArticleAttachmentVO;
    private Boolean haveLike = false;
}
