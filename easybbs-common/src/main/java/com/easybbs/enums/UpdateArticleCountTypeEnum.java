package com.easybbs.enums;

import lombok.Getter;

@Getter
public enum UpdateArticleCountTypeEnum {
    READ_COUNT(0, "阅读数"), GOOD_COUNT(1, "点赞数"), COMMENT_COUNT(2, "评论数");

    private final Integer type;
    private final String desc;

    UpdateArticleCountTypeEnum(Integer type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public static UpdateArticleCountTypeEnum getByType(Integer type) {
        for (UpdateArticleCountTypeEnum item : UpdateArticleCountTypeEnum.values()) {
            if (item.getType().equals(type)) {
                return item;
            }
        }
        return null;
    }
}

