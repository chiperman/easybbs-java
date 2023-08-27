package com.easybbs.dto;

import com.easybbs.annotation.VerifyParam;
import lombok.Data;

@Data
public class LoadUserArticleDto {
    @VerifyParam(required = true)
    private Long userId;
    
    @VerifyParam(required = true)
    private int type;
}
