package com.easybbs.cconst;

public enum EUserStatus {

    INVALID("禁用", 0),
    NORMAL("正常", 1);

    private String desc;
    private Integer code;

    EUserStatus(String desc, Integer code) {
        this.desc = desc;
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
