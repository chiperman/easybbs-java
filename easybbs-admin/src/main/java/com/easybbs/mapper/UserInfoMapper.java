package com.easybbs.mapper;

import com.easybbs.entity.UserInfo;
import com.easybbs.vo.UserQueryVo;
import com.easybbs.vo.UserUpdateVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* @author Master-Z
* @description 针对表【user_info(用户信息)】的数据库操作Mapper
* @createDate 2023-08-18 15:55:28
* @Entity com.easybbs.entity.UserInfo
*/
@Mapper
@Repository
public interface UserInfoMapper {

    int deleteByPrimaryKey(Long id);

    int insert(UserInfo record);

    int insertSelective(UserInfo record);

    UserInfo selectByPrimaryKey(Long id);

    List<UserInfo> loadUserList(UserQueryVo query);

    int updateByPrimaryKeySelective(UserInfo record);

    int updateStatus(UserUpdateVo vo);

    int updateByPrimaryKey(UserInfo record);

}
