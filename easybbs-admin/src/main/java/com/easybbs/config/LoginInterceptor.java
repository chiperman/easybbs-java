package com.easybbs.config;

import com.easybbs.cconst.Constants;
import com.easybbs.cconst.EHttpCode;
import com.easybbs.exception.BusinessException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Value("${login.check}")
    private boolean needCheck;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (needCheck) {
            HttpSession session = request.getSession();
            Object obj = session.getAttribute(Constants.SESSION_KEY);
            if (null == obj) {
                throw new BusinessException(EHttpCode.CODE_901);
            } else {
                return true;
            }
        }
        return true;
    }
}
