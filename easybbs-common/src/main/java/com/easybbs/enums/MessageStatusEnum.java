package com.easybbs.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MessageStatusEnum {
    NO_READ(1, "未读"), READ(2, "已读");

    private final Integer status;
    private final String desc;
}
