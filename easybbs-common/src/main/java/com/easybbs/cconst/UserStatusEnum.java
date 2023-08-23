package com.easybbs.cconst;

import lombok.Getter;

@Getter
public enum UserStatusEnum {
    DISABLE(0, "禁用"), ENABLE(1, "启用");

    private Integer status;
    private String desc;

    UserStatusEnum(Integer status, String desc) {
        this.status = status;
        this.desc = desc;
    }
}
