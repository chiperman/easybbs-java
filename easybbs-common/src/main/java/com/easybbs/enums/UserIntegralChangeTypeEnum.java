package com.easybbs.enums;

import lombok.Getter;

@Getter
public enum UserIntegralChangeTypeEnum {
    ADD(1, "增加"), REDUCE(-1, "减少");


    private Integer changeType;
    private String desc;

    UserIntegralChangeTypeEnum(Integer changeType, String desc) {
        this.changeType = changeType;
        this.desc = desc;
    }

}
