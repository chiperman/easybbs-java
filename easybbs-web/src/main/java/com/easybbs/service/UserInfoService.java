package com.easybbs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.easybbs.cconst.UserIntegralOperTypeEnum;
import com.easybbs.dto.SessionWebUserDto;
import com.easybbs.entity.UserInfo;

/**
 * @author 45083
 * @description 针对表【user_info(用户信息)】的数据库操作Service
 * @createDate 2023-08-20 15:51:45
 */
public interface UserInfoService extends IService<UserInfo> {
    void register(String email, String emailCode, String nickName, String password);

    void updateUserIntegral(String userId, UserIntegralOperTypeEnum operTypeEnum, Integer changeType, Integer integral);

    SessionWebUserDto login(String email, String password, String ip);

    void resetPwd(String email, String password, String emailCode);
}
