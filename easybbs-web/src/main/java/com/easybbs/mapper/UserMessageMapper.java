package com.easybbs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.easybbs.entity.UserMessage;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 45083
 * @description 针对表【user_message(用户消息)】的数据库操作Mapper
 * @createDate 2023-08-24 16:19:25
 * @Entity com.easybbs.entity.UserMessage
 */
@Mapper
public interface UserMessageMapper extends BaseMapper<UserMessage> {

}




