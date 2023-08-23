package com.easybbs.exception;

import com.easybbs.cconst.EHttpCode;
import com.easybbs.response.MyResponse;

public class BusinessException extends RuntimeException {
    private MyResponse<Object> response;


    public BusinessException(EHttpCode httpCode) {
        super(httpCode.getInfo());
        this.response = new MyResponse<>();
        response.setStatus(httpCode.getStatus());
        response.setCode(httpCode.getCode());
        response.setInfo(httpCode.getInfo());
        response.setData(null);
    }

    public BusinessException(String info) {
        super(info);
        this.response = new MyResponse<>();
        response.setStatus(EHttpCode.CODE_600.getStatus());
        response.setCode(EHttpCode.CODE_600.getCode());
        response.setInfo(info);
        response.setData(null);
    }

    public MyResponse<Object> getResponse() {
        return response;
    }
}
