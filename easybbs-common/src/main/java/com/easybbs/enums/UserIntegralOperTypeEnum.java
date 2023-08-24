package com.easybbs.enums;

import lombok.Getter;

@Getter
public enum UserIntegralOperTypeEnum {

    REGISTER(1, "账号注册"),
    USER_DOWNLOAD_ATTACHMENT(2, "下载附件"),
    DOWNLOAD_ATTACHMENT(3, "附件被下载"),
    POST_COMMENT(4, "发表评论"),
    POST_ARTICLE(5, "发布文章"),
    ADMIN(6, "管理员操作"),
    DEL_ARTICLE(7, "文章被删除"),
    DEL_COMMENT(8, "评论被删除");

    private final Integer operType;

    private final String desc;

    UserIntegralOperTypeEnum(Integer operType, String desc) {
        this.operType = operType;
        this.desc = desc;
    }

    private static UserIntegralOperTypeEnum getByType(Integer operType) {
        for (UserIntegralOperTypeEnum typeEnum : UserIntegralOperTypeEnum.values()) {
            if (typeEnum.getOperType().equals(operType)) {
                return typeEnum;
            }
        }
        return null;
    }
}
