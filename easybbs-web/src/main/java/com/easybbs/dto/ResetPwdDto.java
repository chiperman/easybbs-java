package com.easybbs.dto;

import com.easybbs.annotation.VerifyParam;
import com.easybbs.enums.VerifyRegexEnum;
import lombok.Data;

@Data
public class ResetPwdDto {
    @VerifyParam(required = true, regex = VerifyRegexEnum.EMAIL, max = 150)
    private String email;

    @VerifyParam(required = true, regex = VerifyRegexEnum.PASSWORD, min = 8, max = 18)
    private String password;

    @VerifyParam(required = true)
    private String checkCode;

    @VerifyParam(required = true)
    private String emailCode;
}
