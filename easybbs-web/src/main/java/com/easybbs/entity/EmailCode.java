package com.easybbs.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 邮箱验证码
 * @TableName email_code
 */
@TableName(value ="email_code")
@Data
public class EmailCode implements Serializable {
    /**
     * 邮箱
     */
    @TableId
    private String email;

    /**
     * 编号
     */
    @TableId
    private String code;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 0:未使用  1:已使用
     */
    private Integer status;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}