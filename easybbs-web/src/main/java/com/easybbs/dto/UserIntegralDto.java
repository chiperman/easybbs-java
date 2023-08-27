package com.easybbs.dto;

import com.easybbs.request.BasePageQuery;
import lombok.Data;

import java.util.Date;

@Data
public class UserIntegralDto extends BasePageQuery {
    private Integer recordId;

    private String userId;

    private Integer operType;

    private Integer integral;

    private Date createTime;
}
