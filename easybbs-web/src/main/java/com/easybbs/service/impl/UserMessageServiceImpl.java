package com.easybbs.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.easybbs.entity.UserMessage;
import com.easybbs.service.UserMessageService;
import com.easybbs.mapper.UserMessageMapper;
import org.springframework.stereotype.Service;

/**
* @author 45083
* @description 针对表【user_message(用户消息)】的数据库操作Service实现
* @createDate 2023-08-24 16:19:25
*/
@Service
public class UserMessageServiceImpl extends ServiceImpl<UserMessageMapper, UserMessage>
    implements UserMessageService{

}




