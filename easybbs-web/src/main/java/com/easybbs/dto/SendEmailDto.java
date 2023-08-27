package com.easybbs.dto;

import com.easybbs.annotation.VerifyParam;
import com.easybbs.enums.VerifyRegexEnum;
import lombok.Data;

@Data
public class SendEmailDto {
    @VerifyParam(required = true, regex = VerifyRegexEnum.EMAIL, max = 150)
    private String email;

    @VerifyParam(required = true)
    private String checkCode;

    @VerifyParam(required = true)
    private Integer type;
}
