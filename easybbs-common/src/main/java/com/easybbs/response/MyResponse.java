package com.easybbs.response;

import lombok.Data;

@Data
public class MyResponse<T> {
    private String status;
    private Integer code;
    private String info;
    private T data;
}
