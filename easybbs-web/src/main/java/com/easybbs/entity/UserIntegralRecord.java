package com.easybbs.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户积分记录表
 *
 * @TableName user_integral_record
 */
@TableName(value = "user_integral_record")
@Data
public class UserIntegralRecord implements Serializable {
    /**
     * 记录ID
     */
    @TableId(type = IdType.AUTO)
    private Integer recordId;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 操作类型
     */
    private Integer operType;

    /**
     * 积分
     */
    private Integer integral;

    /**
     * 创建时间
     */
    private Date createTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}