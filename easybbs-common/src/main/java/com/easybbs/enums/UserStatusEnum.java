package com.easybbs.enums;

import lombok.Getter;

@Getter
public enum UserStatusEnum {
    DISABLE(0, "禁用"), ENABLE(1, "启用");

    private final Integer status;
    private final String desc;

    UserStatusEnum(Integer status, String desc) {
        this.status = status;
        this.desc = desc;
    }
}
