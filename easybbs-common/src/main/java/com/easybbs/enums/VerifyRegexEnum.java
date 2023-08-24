package com.easybbs.enums;

public enum VerifyRegexEnum {
    NO("", "不校验"),
    IP("^(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." + "(25[0-5]|2[0-4][0-9" + "]|[01]?[0-9][0-9]?)\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$", "IP地址"),
    POSITIVE_INTEGER("^[1-9]\\d*$", "正整数"),
    NUMBER_LETTER_UNDER_LINE("^[a-zA-Z0-9_]*$", "数字、字母、下划线"),
    EMAIL("^\\w+([-+._]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$", "邮箱"),
    PHONE("^1[0-9]{10}$", "手机号"),
    COMMON("^[\\s\\S]*$", "通用正则"),
    PASSWORD("^(?=.*\\d)(?=.*[a-zA-Z])[\\da-zA-Z~!@#$%^&*_]{8,18}$", "只能是数字，字母，特殊字符 8-18 位"),
    ACCOUNT("^[a-zA-Z][a-zA-Z0-9_]{5,15}$", "账号（以字母开头，6-16位数字、字母、下划线）"),
    MONEY("^\\d+(\\.\\d{1,2})?$", "金额");

    private final String regex;
    private final String desc;

    VerifyRegexEnum(String regex, String desc) {
        this.regex = regex;
        this.desc = desc;
    }

    public String getRegex() {
        return regex;
    }

    public String getDesc() {
        return desc;
    }
}
