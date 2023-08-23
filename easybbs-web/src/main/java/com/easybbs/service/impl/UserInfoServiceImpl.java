package com.easybbs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.easybbs.cconst.*;
import com.easybbs.dto.SessionWebUserDto;
import com.easybbs.entity.UserInfo;
import com.easybbs.entity.UserIntegralRecord;
import com.easybbs.exception.BusinessException;
import com.easybbs.mapper.UserInfoMapper;
import com.easybbs.mapper.UserIntegralRecordMapper;
import com.easybbs.service.EmailCodeService;
import com.easybbs.service.UserInfoService;
import com.easybbs.utils.StringTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Autowired
    UserIntegralRecordMapper userIntegralRecordMapper;

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

        // 更新用户积分
        updateUserIntegral(userId, UserIntegralOperTypeEnum.REGISTER, UserIntegralChangeTypeEnum.ADD.getChangeType(), ConstNumber.INTEGRAL_5);
    }

    /**
     * 更新用户积分
     *
     * @param userId
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateUserIntegral(String userId, UserIntegralOperTypeEnum operTypeEnum, Integer changeType,
                                   Integer integral) {

        integral = changeType * integral;
        if (integral == 0) {
            return;
        }

        UserInfo userInfo = userInfoMapper.selectById(userId);


        if (UserIntegralChangeTypeEnum.REDUCE.getChangeType().equals(changeType) && userInfo.getCurrentIntegral() + integral < 0) {
            integral = changeType * userInfo.getCurrentIntegral();
        }

        UserIntegralRecord record = new UserIntegralRecord();
        record.setUserId(userId);
        record.setOperType(operTypeEnum.getOperType());
        record.setCreateTime(new Date());
        record.setIntegral(integral);
        userIntegralRecordMapper.insert(record);

        Integer count = userInfoMapper.updateIntegral(Long.valueOf(userId), integral);
        if (count == 0) {
            throw new BusinessException("更新用户积分失败");
        }
    }

    @Override
    public SessionWebUserDto login(String email, String password, String ip) {
        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("eamil", email);
        UserInfo userInfo = userInfoMapper.selectOne(queryWrapper);

        if (null == userInfo || !userInfo.getPassword().equals(password)) {
            throw new BusinessException("账号或者密码错误");
        }

        if (UserStatusEnum.DISABLE.getStatus().equals(userInfo.getStatus())) {
            throw new BusinessException("账号已禁用");
        }

        
        return null;
    }
}




