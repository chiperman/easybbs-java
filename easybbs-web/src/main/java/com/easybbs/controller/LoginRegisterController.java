package com.easybbs.controller;

import com.easybbs.annotation.GlobalInterceptor;
import com.easybbs.cconst.Constants;
import com.easybbs.cconst.EHttpCode;
import com.easybbs.dto.*;
import com.easybbs.exception.BusinessException;
import com.easybbs.response.MyResponse;
import com.easybbs.service.EmailCodeService;
import com.easybbs.service.UserInfoService;
import com.easybbs.utils.CreateImageCode;
import com.easybbs.utils.SetResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
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
    public MyResponse<Object> sendEmailCode(HttpSession session, @RequestBody SendEmailDto sendEmailDto) {
        try {
            MyResponse<Object> response = new MyResponse<>();
            String email = sendEmailDto.getEmail();
            String checkCode = sendEmailDto.getCheckCode();
            Integer type = sendEmailDto.getType();
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
    public MyResponse<Object> register(HttpSession session, @RequestBody RegisterRequestDTO registerRequestDTO) {
        try {
            MyResponse<Object> response = new MyResponse<>();
            String email = registerRequestDTO.getEmail();
            String emailCode = registerRequestDTO.getEmailCode();
            String nickName = registerRequestDTO.getNickName();
            String password = registerRequestDTO.getPassword();
            String checkCode = registerRequestDTO.getCheckCode();
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
    public MyResponse<Object> login(HttpSession session, HttpServletRequest request, @RequestBody LoginDto loginDto) {
        try {
            MyResponse<Object> response = new MyResponse<>();

            String email = loginDto.getEmail();
            String password = loginDto.getPassword();
            String checkCode = loginDto.getCheckCode();

            if (!checkCode.equalsIgnoreCase((String) session.getAttribute(Constants.CHECK_CODE_KEY))) {
                throw new BusinessException(EHttpCode.CODE_601);
            }
            SessionWebUserDto sessionWebUserDto = userInfoService.login(email,
                    password,
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
    public MyResponse<Object> resetPwd(HttpSession session, @RequestBody ResetPwdDto resetPwdDto) {
        try {

            String email = resetPwdDto.getEmail();
            String password = resetPwdDto.getPassword();
            String checkCode = resetPwdDto.getCheckCode();
            String emailCode = resetPwdDto.getEmailCode();

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
