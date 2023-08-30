package com.easybbs.mapper;

import com.easybbs.entity.UserMessage;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author Master-Z
* @description 针对表【user_message(用户消息)】的数据库操作Mapper
* @createDate 2023-08-29 16:56:03
* @Entity com.easybbs.entity.UserMessage
*/
@Mapper
public interface UserMessageMapper extends BaseMapper<UserMessage> {

}




