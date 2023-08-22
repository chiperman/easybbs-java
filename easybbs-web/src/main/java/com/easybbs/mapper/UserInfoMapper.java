package com.easybbs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.easybbs.entity.UserInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 45083
 * @description 针对表【user_info(用户信息)】的数据库操作Mapper
 * @createDate 2023-08-20 15:51:45
 * @Entity com.easybbs.entity.UserInfo
 */
@Mapper
public interface UserInfoMapper extends BaseMapper<UserInfo> {

}




