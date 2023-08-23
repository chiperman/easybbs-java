package com.easybbs.dto;

import lombok.Data;
import com.easybbs.request.BasePageQuery;

import java.util.Date;

@Data
public class ForumArticleDto extends BasePageQuery {
    private String articleId;
    private Integer boardId;
    private String boardName;
    private Integer pBoardId;
    private String pBoardName;
    private Long userId;
    private String nickName;
    private String userIpAddress;
    private String title;
    private String cover;
    private String content;
    private String summary;
    private Date postTime;
    private Integer readCount;
    private Integer goodCount;
    private Integer commentCount;
    private Integer topType;
    private Integer attachmentType;
    private Integer status;
}
