package com.easybbs.controller;

import cconst.CheckCode;
import com.easybbs.exception.BusinessException;
import com.easybbs.service.EmailCodeService;
import com.easybbs.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import response.MyResponse;
import utils.CreateImageCode;
import utils.SetResponseUtils;
import utils.StringTools;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@RestController
public class LoginRegisterController {

    @Autowired
    EmailCodeService emailCodeService;

    @Autowired
    UserInfoService userInfoService;

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
            session.setAttribute(CheckCode.CHECK_CODE_KEY, code);
        } else {
            // 获取邮箱
            session.setAttribute(CheckCode.CHECK_CODE_EMAIL, code);
        }
        System.out.println(code);
        vCode.write(response.getOutputStream());
    }

    @RequestMapping(value = "sendEmailCode")
    public MyResponse<Object> sendEmailCode(HttpSession session, String email, String checkCode, Integer type) {
        try {
            MyResponse<Object> response = new MyResponse<>();
            if (StringTools.isEmpty(email) || StringTools.isEmpty(checkCode) || type == null) {
                throw new BusinessException("");
            }
            if (!checkCode.equalsIgnoreCase((String) session.getAttribute(CheckCode.CHECK_CODE_KEY))) {
                throw new BusinessException("图片验证码错误");
            }
            emailCodeService.sendEmailCode(email, type);
            SetResponseUtils.setResponseSuccess(response, null);
            return response;
        } finally {
            session.removeAttribute(CheckCode.CHECK_CODE_EMAIL);
        }

    }

    @RequestMapping(value = "/register")
    public MyResponse<Object> register(HttpSession session, String email, String emailCode, String nickName,
                                       String password, String checkCode) {
        try {
            MyResponse<Object> response = new MyResponse<>();
            if (StringTools.isEmpty(email) || StringTools.isEmpty(emailCode) || StringTools.isEmpty(nickName) || StringTools.isEmpty(password) || StringTools.isEmpty(checkCode)) {
                throw new BusinessException("请求参数错误");
            }
            if (!checkCode.equalsIgnoreCase((String) session.getAttribute(CheckCode.CHECK_CODE_KEY))) {
                throw new BusinessException("验证码错误");
            }
            userInfoService.register(email, emailCode, nickName, password);
            SetResponseUtils.setResponseSuccess(response, null);
            return response;
        } finally {
            session.removeAttribute(CheckCode.CHECK_CODE_KEY);
        }
    }
}
