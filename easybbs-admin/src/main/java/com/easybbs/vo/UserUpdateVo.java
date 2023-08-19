package com.easybbs.vo;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UserUpdateVo {
    @NotNull
    private Integer status;
    @NotNull
    private Long userId;
}
