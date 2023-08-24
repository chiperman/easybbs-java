package com.easybbs.controller;

import com.easybbs.annotation.GlobalInterceptor;
import com.easybbs.annotation.VerifyParam;
import com.easybbs.cconst.Constants;
import com.easybbs.cconst.EHttpCode;
import com.easybbs.dto.SessionWebUserDto;
import com.easybbs.enums.VerifyRegexEnum;
import com.easybbs.exception.BusinessException;
import com.easybbs.response.MyResponse;
import com.easybbs.service.EmailCodeService;
import com.easybbs.service.UserInfoService;
import com.easybbs.utils.CreateImageCode;
import com.easybbs.utils.SetResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@RestController
public class LoginRegisterController {

    @Autowired
    EmailCodeService emailCodeService;

    @Autowired
    UserInfoService userInfoService;

    @Autowired
    BaseController baseController;

    /**
     * 验证码
     */
    @RequestMapping(value = "/checkCode")
    public void checkCode(HttpServletResponse response, HttpSession session, Integer type) throws IOException {
        // 生成验证码图片
        CreateImageCode vCode = new CreateImageCode(130, 38, 5, 10);
        response.setHeader("Pargma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");
        String code = vCode.getCode();
        if (type == null || type == 0) {
            // 登陆注册
            session.setAttribute(Constants.CHECK_CODE_KEY, code);
        } else {
            // 获取邮箱
            session.setAttribute(Constants.CHECK_CODE_EMAIL, code);
        }
        System.out.println(code);
        vCode.write(response.getOutputStream());
    }

    @RequestMapping(value = "sendEmailCode")
    @GlobalInterceptor(checkParams = true)
    public MyResponse<Object> sendEmailCode(HttpSession session, @VerifyParam(required = true) String email,
                                            @VerifyParam(required = true) String checkCode,
                                            @VerifyParam(required = true) Integer type) {
        try {
            MyResponse<Object> response = new MyResponse<>();
            if (!checkCode.equalsIgnoreCase((String) session.getAttribute(Constants.CHECK_CODE_KEY))) {
                throw new BusinessException("图片验证码错误");
            }
            emailCodeService.sendEmailCode(email, type);
            SetResponseUtils.setResponseSuccess(response, null);
            return response;
        } finally {
            session.removeAttribute(Constants.CHECK_CODE_EMAIL);
        }

    }

    @RequestMapping(value = "/register")
    @GlobalInterceptor(checkParams = true)
    public MyResponse<Object> register(HttpSession session,
                                       @VerifyParam(required = true, regex = VerifyRegexEnum.EMAIL, max = 150) String email,
                                       @VerifyParam(required = true) String emailCode,
                                       @VerifyParam(required = true, max = 20) String nickName,
                                       @VerifyParam(required = true, regex = VerifyRegexEnum.PASSWORD, min = 8, max =
                                               18) String password,
                                       @VerifyParam(required = true) String checkCode) {
        try {
            MyResponse<Object> response = new MyResponse<>();
            if (!checkCode.equalsIgnoreCase((String) session.getAttribute(Constants.CHECK_CODE_KEY))) {
                throw new BusinessException(EHttpCode.CODE_601);
            }
            userInfoService.register(email, emailCode, nickName, password);
            SetResponseUtils.setResponseSuccess(response, null);
            return response;
        } finally {
            session.removeAttribute(Constants.CHECK_CODE_KEY);
        }
    }

    @RequestMapping(value = "/login")
    @GlobalInterceptor(checkParams = true)
    public MyResponse<Object> login(HttpSession session, HttpServletRequest request,
                                    @VerifyParam(required = true) String email,
                                    @VerifyParam(required = true) String password,
                                    @VerifyParam(required = true) String checkCode) {
        try {
            MyResponse<Object> response = new MyResponse<>();
            if (!checkCode.equalsIgnoreCase((String) session.getAttribute(Constants.CHECK_CODE_KEY))) {
                throw new BusinessException(EHttpCode.CODE_601);
            }
            SessionWebUserDto sessionWebUserDto = userInfoService.login(email, password,
                    baseController.getIpAddr(request));
            session.setAttribute(Constants.SESSION_KEY, sessionWebUserDto);
            SetResponseUtils.setResponseSuccess(response, sessionWebUserDto);
            return response;
        } finally {
            session.removeAttribute(Constants.CHECK_CODE_KEY);
        }
    }

    @RequestMapping(value = "/resetPwd")
    @GlobalInterceptor(checkParams = true)
    public MyResponse<Object> resetPwd(HttpSession session, @VerifyParam(required = true) String email,
                                       @VerifyParam(required = true, regex = VerifyRegexEnum.PASSWORD, min = 8, max =
                                               18) String password,
                                       @VerifyParam(required = true) String checkCode,
                                       @VerifyParam(required = true) String emailCode) {
        try {
            if (!checkCode.equalsIgnoreCase((String) session.getAttribute(Constants.CHECK_CODE_KEY))) {
                throw new BusinessException(EHttpCode.CODE_601);
            }
            MyResponse<Object> response = new MyResponse<>();
            userInfoService.resetPwd(email, password, emailCode);
            SetResponseUtils.setResponseSuccess(response, null);
            return response;
        } finally {
            session.removeAttribute(Constants.CHECK_CODE_KEY);
        }
    }


}
