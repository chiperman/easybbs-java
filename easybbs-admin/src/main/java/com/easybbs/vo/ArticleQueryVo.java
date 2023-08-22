package com.easybbs.vo;

import lombok.Data;
import request.BasePageQuery;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class ArticleQueryVo extends BasePageQuery {
    @NotBlank(groups = {DeleteArticle.class, TopArticle.class, DelComment.class},
            message = "articleId不能为空")
    private String articleId;
    @NotNull(groups = {DelComment.class}, message = "commentId不能为空")
    private Integer commentId;
    private String titleFuzzy;
    private String nickNameFuzzy;
    private Integer attachmentType;
    private Integer status;
    @NotNull(groups = {TopArticle.class}, message = "topType不能为空")
    private Integer topType;

    public @interface DeleteArticle {}
    public @interface TopArticle {}

    public @interface DelComment {}
}
