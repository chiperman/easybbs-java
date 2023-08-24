package com.easybbs.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OperRecordOpTypeEnum {
    ARTICLE_LIKE(0, "文字点赞"), COMMENT_LIKE(1, "评论点赞");

    private final Integer type;
    private final String desc;


}
