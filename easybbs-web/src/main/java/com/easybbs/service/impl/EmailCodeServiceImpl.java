package com.easybbs.service.impl;

import com.easybbs.cconst.ConstNumber;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.easybbs.config.WebConfig;
import com.easybbs.entity.EmailCode;
import com.easybbs.entity.UserInfo;
import com.easybbs.exception.BusinessException;
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
import com.easybbs.utils.StringTools;

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
        if (ConstNumber.ZERO == 0) {
            // 在用户表中查询是否存在邮箱，判断用户是否已经注册
            QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("email", email);
            UserInfo userInfo = userInfoMapper.selectOne(queryWrapper);
            if (null != userInfo) {
                throw new BusinessException("邮箱已存在");
            }
        }

        String code = StringTools.getRandomString(ConstNumber.LENGHT_5);
        sendEmailCodeDo(email, code);

        emailCodeMapper.disableEmailCode(email);

        EmailCode emailCode = new EmailCode();
        emailCode.setCode(code);
        emailCode.setEmail(email);
        emailCode.setStatus(ConstNumber.ZERO);
        emailCode.setCreateTime(new Date());
        emailCodeMapper.insert(emailCode);
    }

    @Override
    public void checkCode(String email, String emailCode) {
        QueryWrapper<EmailCode> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("email", email).eq("code", emailCode);
        EmailCode dbInfo = emailCodeMapper.selectOne(queryWrapper);

        if (null == dbInfo) {
            throw new BusinessException("邮箱验证码不正确");
        }

        if (dbInfo.getStatus() != ConstNumber.ZERO || System.currentTimeMillis() - dbInfo.getCreateTime().getTime() > 1000 * 60 * ConstNumber.LENGHT_15) {
            throw new BusinessException("邮箱验证码已失效");
        }
        emailCodeMapper.disableEmailCode(email);
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
            throw new BusinessException("邮件发送失败");
        }
    }
}




