package com.easybbs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.easybbs.entity.EmailCode;

/**
 * @author 45083
 * @description 针对表【email_code(邮箱验证码)】的数据库操作Service
 * @createDate 2023-08-21 17:58:12
 */
public interface EmailCodeService extends IService<EmailCode> {
    void sendEmailCode(String email, Integer type);

    void checkCode(String email, String emailCode);
}
