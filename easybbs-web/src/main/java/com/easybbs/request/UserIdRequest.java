package com.easybbs.request;

import com.easybbs.annotation.VerifyParam;
import lombok.Data;

@Data
public class UserIdRequest {
    @VerifyParam(required = true)
    private Long userId;
}