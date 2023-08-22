package com.easybbs.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ArticleCommentDto {
    private Integer commentId;
    private Integer pCommentId;
    private String articleId;
    private String content;
    private String imgPath;
    private Long userId;
    private String nickName;
    private String userIpAddress;
    private String replyUserId;
    private String replyNickName;
    private Integer topType;
    private Date postTime;
    private Integer goodCount;
    private Integer status;
    private List<ArticleCommentDto> children;
}
