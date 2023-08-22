package com.easybbs.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class ArticleBoardVo {
    @NotBlank(message = "articleId不能为空")
    private String articleId;
    @NotNull(message = "pboardId不能为空")
    private Integer pboardId;
    @NotNull(message = "boardId不能为空")
    private Integer boardId;
}
