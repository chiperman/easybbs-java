package com.easybbs.dto;

import com.easybbs.annotation.VerifyParam;
import com.easybbs.enums.VerifyRegexEnum;
import lombok.Data;

@Data
public class LoginDto {
    @VerifyParam(required = true, regex = VerifyRegexEnum.EMAIL, max = 150)
    private String email;

    @VerifyParam(required = true)
    private String password;

    @VerifyParam(required = true)
    private String checkCode;
}
