package com.easybbs.dto;
import java.util.Date;
import lombok.Data;

@Data
public class UserInfoDto {

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 密码
     */
    private String password;

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
     * 最后登录IP
     */
    private String lastLoginIp;

    /**
     * 最后登录ip地址
     */
    private String lastLoginIpAddress;

    /**
     * 积分
     */
    private Integer totalIntegral;

    /**
     * 当前积分
     */
    private Integer currentIntegral;

    /**
     * 0:禁用 1:正常
     */
    private Integer status;
}
