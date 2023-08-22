package com.easybbs.service;

import cconst.UserIntegralOperTypeEnum;
import com.baomidou.mybatisplus.extension.service.IService;
import com.easybbs.entity.UserInfo;

/**
 * @author 45083
 * @description 针对表【user_info(用户信息)】的数据库操作Service
 * @createDate 2023-08-20 15:51:45
 */
public interface UserInfoService extends IService<UserInfo> {
    void register(String email, String emailCode, String nickName, String password);

    void updateUserIntegral(String userId, UserIntegralOperTypeEnum operTypeEnum, Integer changeType, Integer integral);
}
