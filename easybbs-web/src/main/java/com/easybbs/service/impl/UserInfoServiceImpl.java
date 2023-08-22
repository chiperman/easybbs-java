package com.easybbs.service.impl;

import cconst.ConstNumber;
import cconst.EUserStatus;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.easybbs.entity.UserInfo;
import com.easybbs.exception.BusinessException;
import com.easybbs.mapper.UserInfoMapper;
import com.easybbs.service.EmailCodeService;
import com.easybbs.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utils.StringTools;

import java.util.Date;

/**
 * @author 45083
 * @description 针对表【user_info(用户信息)】的数据库操作Service实现
 * @createDate 2023-08-20 15:51:45
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService {

    @Autowired
    UserInfoMapper userInfoMapper;

    @Autowired
    EmailCodeService emailCodeService;

    @Override
    public void register(String email, String emailCode, String nickName, String password) {
        // 1. 先查 userInfo
        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("email");
        queryWrapper.eq("email", email);
        UserInfo userInfo = userInfoMapper.selectOne(queryWrapper);
        System.out.println(userInfo);
        if (null != userInfo) {
            throw new BusinessException("邮箱账号已经存在");
        }

        QueryWrapper<UserInfo> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.select("nick_name");
        queryWrapper1.eq("nick_name", nickName);
        userInfo = userInfoMapper.selectOne(queryWrapper1);
        if (null != userInfo) {
            throw new BusinessException("昵称已存在");
        }

        emailCodeService.checkCode(email, emailCode);

        String userId = StringTools.getRandomNumber(ConstNumber.LENGTH_10);

        UserInfo insertInfo = new UserInfo();
        insertInfo.setUserId(Long.valueOf(userId));
        insertInfo.setNickName(nickName);
        insertInfo.setEmail(email);
        insertInfo.setPassword(StringTools.encodeMd5(password));
        insertInfo.setJoinTime(new Date());
        insertInfo.setStatus(EUserStatus.NORMAL.getCode());
        insertInfo.setTotalIntegral(ConstNumber.ZERO);
        insertInfo.setCurrentIntegral(ConstNumber.ZERO);

        userInfoMapper.insert(insertInfo);
    }
}




