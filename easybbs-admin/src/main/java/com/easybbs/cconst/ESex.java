package com.easybbs.cconst;

public enum ESex {
    FEMALE("女", 0),
    MALE("男", 1);

    private String desc;
    private Integer code;

    ESex(String desc, Integer code) {
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
