package com.easybbs.dto;

import com.easybbs.annotation.VerifyParam;
import lombok.Data;

@Data
public class ArticleIdDto {
    @VerifyParam(required = true)
    private String articleId;
}
