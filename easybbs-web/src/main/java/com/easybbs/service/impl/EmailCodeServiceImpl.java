package com.easybbs.service.impl;

import cconst.CheckCode;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.easybbs.config.WebConfig;
import com.easybbs.entity.EmailCode;
import com.easybbs.entity.UserInfo;
import com.easybbs.exception.EmailAlreadyExistsException;
import com.easybbs.mapper.EmailCodeMapper;
import com.easybbs.mapper.UserInfoMapper;
import com.easybbs.service.EmailCodeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import utils.StringTools;

import javax.mail.internet.MimeMessage;
import java.util.Date;

/**
 * @author 45083
 * @description 针对表【email_code(邮箱验证码)】的数据库操作Service实现
 * @createDate 2023-08-21 17:58:12
 */
@Service
public class EmailCodeServiceImpl extends ServiceImpl<EmailCodeMapper, EmailCode> implements EmailCodeService {

    private static final Logger logger = LoggerFactory.getLogger(EmailCodeServiceImpl.class);

    @Autowired
    UserInfoMapper userInfoMapper;

    @Autowired
    JavaMailSender javaMailSender;

    @Autowired
    EmailCodeMapper emailCodeMapper;

    @Autowired
    WebConfig webConfig;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void sendEmailCode(String email, Integer type) {
        if (CheckCode.ZERO == 0) {
            QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("email", email);
            UserInfo userInfo = userInfoMapper.selectOne(queryWrapper);
            if (userInfo != null) {
                // TODO: 返回一个“邮箱已经存在”
                throw new EmailAlreadyExistsException("邮箱已存在");
            }
        }

        String code = StringTools.getRandomString(CheckCode.LENGHT_5);
        // sendEmailCodeDo(email, code);

        emailCodeMapper.disableEmailCode(email);

        EmailCode emailCode = new EmailCode();
        emailCode.setCode(code);
        emailCode.setEmail(email);
        emailCode.setStatus(CheckCode.ZERO);
        emailCode.setCreateTime(new Date());
        emailCodeMapper.insert(emailCode);
    }

    /**
     * 发邮件
     *
     * @param toEmail
     * @param code
     */
    private void sendEmailCodeDo(String toEmail, String code) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            // 发件人
            helper.setFrom(webConfig.getSendUserName());
            // 收件人
            helper.setTo(toEmail);
            helper.setSubject("注册邮箱验证码");
            helper.setText("邮箱验证码为：" + code);
            helper.setSentDate(new Date());
            javaMailSender.send(message);
        } catch (Exception e) {
            logger.error("发送邮件失败");
        }
    }
}




