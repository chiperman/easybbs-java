package com.easybbs.controller;

import cconst.CheckCode;
import com.easybbs.service.EmailCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import response.MyResponse;
import utils.CreateImageCode;
import utils.SetResponseUtils;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@RestController
public class LoginRegisterController {

    @Autowired
    EmailCodeService emailCodeService;

    /**
     * 验证码
     */
    @RequestMapping(value = "/checkCode")
    public void checkCode(HttpServletResponse response, HttpSession session, Integer type) throws IOException {
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
        MyResponse<Object> response = new MyResponse<>();

        // if (StringTools.isEmpty(email) || StringTools.isEmpty(checkCode) || type == null) {
        //     SetResponseUtils.setResponseFailParam(response, null);
        //     return response;
        // }
        emailCodeService.sendEmailCode(email, type);
        SetResponseUtils.setResponseSuccess(response, null);
        return response;
    }

    @RequestMapping(value = "/register")
    public MyResponse<Object> register(HttpSession session, String checkCode) {
        MyResponse<Object> response = new MyResponse<>();
        if (checkCode == null || "".equals(checkCode)) {
            SetResponseUtils.setResponseFailParam(response, null);
            return response;
        }
        String sessionCode = (String) session.getAttribute(CheckCode.CHECK_CODE_KEY);
        if (sessionCode.equals(checkCode)) {
            // 返回验证成功
            SetResponseUtils.setResponseSuccess(response, null);
        } else {
            // 返回验证失败
            SetResponseUtils.setResponseFail(response, null);
        }
        return response;
    }

    @RequestMapping("/test")
    public void test() {
        emailCodeService.sendEmailCode("test02@qeq.com", 0);
    }
}
