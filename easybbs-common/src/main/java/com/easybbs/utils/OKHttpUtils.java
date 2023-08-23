package com.easybbs.utils;

import com.easybbs.cconst.EHttpCode;
import com.easybbs.exception.BusinessException;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class OKHttpUtils {
    private static final int TIME_OUT_SECONDS = 5;

    private static Logger logger = LoggerFactory.getLogger(OKHttpUtils.class);

    private static OkHttpClient.Builder getClientBuilder() {
        OkHttpClient.Builder clientBuilder =
                new OkHttpClient.Builder().followRedirects(false).retryOnConnectionFailure(false);
        clientBuilder.connectTimeout(TIME_OUT_SECONDS, TimeUnit.SECONDS).readTimeout(TIME_OUT_SECONDS,
                TimeUnit.SECONDS);
        return clientBuilder;
    }

    private static Request.Builder getRequestBuilder(Map<String, String> header) {
        Request.Builder requestBuilder = new Request.Builder();
        if (null != header) {
            for (Map.Entry<String, String> map : header.entrySet()) {
                String key = map.getKey();
                String value;
                if (map.getValue() == null) {
                    value = "";
                } else {
                    value = map.getValue();
                }
                requestBuilder.addHeader(key, value);
            }
        }
        return requestBuilder;
    }

    private static FormBody.Builder getBuilder(Map<String, String> params) {
        FormBody.Builder builder = new FormBody.Builder();
        if (null != params) {
            return builder;
        }
        for (Map.Entry<String, String> map : params.entrySet()) {
            String key = map.getKey();
            String value;
            if (map.getValue() == null) {
                value = "";
            } else {
                value = map.getValue();
            }
            builder.add(key, value);
        }
        return builder;
    }

    private static String getRequest(String url) throws BusinessException {
        ResponseBody responseBody = null;
        try {
            OkHttpClient.Builder clientBuilder = getClientBuilder();
            Request.Builder requestBuilder = getRequestBuilder(null);
            OkHttpClient client = clientBuilder.build();
            Request request = requestBuilder.url(url).build();
            Response response = client.newCall(request).execute();
            responseBody = response.body();
            return responseBody.string();
        } catch (SocketTimeoutException | ConnectException e) {
            logger.error("OKhttp POST 请求超时，url:{}", url, e);
            throw new BusinessException(EHttpCode.CODE_900);
        } catch (Exception e) {
            logger.error("OKhttp GET 异常", e);
            throw null;
        } finally {
            if (responseBody != null) {
                responseBody.close();
            }
        }
    }
}
