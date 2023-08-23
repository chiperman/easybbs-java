package com.easybbs.dto;

import lombok.Data;
import com.easybbs.request.BasePageQuery;

import java.util.Date;

@Data
public class UserIntegralDto extends BasePageQuery {
    private Integer recordId;
    private String userId;
    private Integer operType;
    private Integer integral;
    private Date createTime;
}
