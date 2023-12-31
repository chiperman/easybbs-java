package com.easybbs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.easybbs.entity.ForumArticle;

/**
 * @author 45083
 * @description 针对表【forum_article(文章信息)】的数据库操作Service
 * @createDate 2023-08-20 14:28:38
 */
public interface ForumArticleService extends IService<ForumArticle> {

    ForumArticle readArticle(String articleId);
}
