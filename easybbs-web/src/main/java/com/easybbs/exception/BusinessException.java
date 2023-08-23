package com.easybbs.exception;

import com.easybbs.response.MyResponse;

public class BusinessException extends RuntimeException {
    private MyResponse<Object> response;

    public BusinessException(String message) {
        super(message);
        this.response = new MyResponse<>();
        response.setStatus("fail");
        response.setCode(600);
        response.setInfo(message);
        response.setData(null);
    }

    public MyResponse<Object> getResponse() {
        return response;
    }
}
