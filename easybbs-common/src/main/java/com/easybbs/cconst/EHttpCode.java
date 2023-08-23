package com.easybbs.cconst;

public enum EHttpCode {
    SUCCESS(200, "请求成功", "success"),
    FAIL(603, "更新失败", "fail"),
    CODE_600(600, "请求参数错误", "error"),
    CODE_601(601, "验证码错误", "error"),
    CODE_900(900, "Http 请求超时", "error");

    private Integer code;
    private String info;
    private String status;

    EHttpCode(Integer code, String info, String status) {
        this.code = code;
        this.info = info;
        this.status = status;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
