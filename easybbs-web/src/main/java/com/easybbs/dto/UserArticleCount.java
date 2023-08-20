package com.easybbs.dto;

import lombok.Data;

@Data
public class UserArticleCount {
    private int postCount; // 发帖数
    private int likeCount; // 收到的点赞数
}
