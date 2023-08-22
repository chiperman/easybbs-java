package com.easybbs.vo;

import lombok.Data;

import java.util.Date;

@Data
public class UserInfoResponseVO {
    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 昵称
     */
    private String nickName;


    /**
     * 0:女 1:男
     */
    private Integer sex;

    /**
     * 个人描述
     */
    private String personDescription;

    /**
     * 加入时间
     */
    private Date joinTime;

    /**
     * 最后登录时间
     */
    private Date lastLoginTime;
    /**
     * 发帖数
     */
    private Integer postCount;

    /**
     * 收到的点赞数
     */
    private Integer likeCount;

    /**
     * 当前积分
     */
    private Integer currentIntegral;
}
