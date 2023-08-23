package com.easybbs.utils;

import com.easybbs.cconst.EHttpCode;
import com.easybbs.response.MyResponse;

public class SetResponseUtils {
    public static void setResponseSuccess(MyResponse response, Object data) {
        setResponse(response, EHttpCode.SUCCESS, data);
    }

    public static void setResponseFail(MyResponse response, Object data) {
        setResponse(response, EHttpCode.FAIL, data);
    }

    public static void setResponseFailParam(MyResponse response, Object data) {
        setResponse(response, EHttpCode.FAIL_Params, data);
    }


    private static void setResponse(MyResponse response, EHttpCode httpCode, Object data) {
        response.setStatus(httpCode.getStatus());
        response.setCode(httpCode.getCode());
        response.setInfo(httpCode.getInfo());
        response.setData(data);
    }
}
