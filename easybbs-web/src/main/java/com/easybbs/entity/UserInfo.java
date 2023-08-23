package com.easybbs.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户信息
 *
 * @TableName user_info
 */
@TableName(value = "user_info")
@Data
public class UserInfo implements Serializable {
    /**
     * 用户ID
     */
    // @TableId(type = IdType.AUTO)
    // @TableField(value = "user_id")
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

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}