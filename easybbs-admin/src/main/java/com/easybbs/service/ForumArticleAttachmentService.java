package com.easybbs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.easybbs.entity.ForumArticleAttachment;

/**
* @author 45083
* @description 针对表【forum_article_attachment(文件信息)】的数据库操作Service
* @createDate 2023-08-24 14:13:29
*/
public interface ForumArticleAttachmentService extends IService<ForumArticleAttachment> {

    ForumArticleAttachment getAttachmentByArticleId(String articleId);

}
