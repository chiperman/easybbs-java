package com.easybbs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.easybbs.entity.ForumArticleAttachment;
import com.easybbs.mapper.ForumArticleAttachmentMapper;
import com.easybbs.service.ForumArticleAttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* @author 45083
* @description 针对表【forum_article_attachment(文件信息)】的数据库操作Service实现
* @createDate 2023-08-24 14:13:29
*/
@Service
public class ForumArticleAttachmentServiceImpl extends ServiceImpl<ForumArticleAttachmentMapper, ForumArticleAttachment>
    implements ForumArticleAttachmentService{
    @Autowired
    private ForumArticleAttachmentMapper attachmentMapper;
    @Override
    public ForumArticleAttachment getAttachmentByArticleId(String articleId) {
        LambdaQueryWrapper<ForumArticleAttachment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ForumArticleAttachment::getArticleId, articleId);
        ForumArticleAttachment attachment = attachmentMapper.selectOne(lambdaQuery());
        return attachment;
    }
}




