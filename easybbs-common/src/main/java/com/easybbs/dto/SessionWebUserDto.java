package com.easybbs.dto;

import lombok.Data;

@Data
public class SessionWebUserDto {
    private String nickName;
    private String province;
    private String userId;
    private Boolean isAdmin;
}
