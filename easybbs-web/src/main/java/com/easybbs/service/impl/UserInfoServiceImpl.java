package com.easybbs.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.easybbs.entity.UserInfo;
import com.easybbs.service.UserInfoService;
import com.easybbs.mapper.UserInfoMapper;
import org.springframework.stereotype.Service;

/**
* @author 45083
* @description 针对表【user_info(用户信息)】的数据库操作Service实现
* @createDate 2023-08-20 15:51:45
*/
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo>
    implements UserInfoService{

}




