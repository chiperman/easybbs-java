package cconst;

public enum EUserStatus {

    INVALID("禁用", 0), NORMAL("正常", 1);

    private String desc;
    private Integer code;

    EUserStatus(String desc, Integer code) {
        this.desc = desc;
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public Integer getCode() {
        return code;
    }
}
