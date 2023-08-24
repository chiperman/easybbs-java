package com.easybbs.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MessageTypeEnum {
    SYS(0, "sys", "系统信息"),
    COMMENT(1, "reply", "回复我的"),
    ARTICLE_LIKE(2, "likePost", "赞了我的文章"),
    COMMENT_LIKE(3, "likeComment", "赞了我的评论"),
    DOWNLOAD_ATTACHMENT(4, "downloadAttachment", "下载附件");

    private final Integer type;
    private final String operation;
    private final String desc;


}
