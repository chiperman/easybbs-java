package com.easybbs.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.easybbs.entity.ForumComment;
import com.easybbs.service.ForumCommentService;
import com.easybbs.mapper.ForumCommentMapper;
import org.springframework.stereotype.Service;

/**
* @author Master-Z
* @description 针对表【forum_comment(评论)】的数据库操作Service实现
* @createDate 2023-08-20 12:01:58
*/
@Service
public class ForumCommentServiceImpl extends ServiceImpl<ForumCommentMapper, ForumComment>
    implements ForumCommentService{

}




