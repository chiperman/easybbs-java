package com.easybbs.service;

import com.easybbs.dto.ArticleCommentDto;
import com.easybbs.entity.ForumArticle;
import com.baomidou.mybatisplus.extension.service.IService;
import com.easybbs.vo.ArticleBoardVo;
import com.easybbs.vo.ArticleQueryVo;
import com.easybbs.response.PageResult;

/**
 * @author Master-Z
 * @description 针对表【forum_article(文章信息)】的数据库操作Service
 * @createDate 2023-08-20 10:26:16
 */
public interface ForumArticleService extends IService<ForumArticle> {

    PageResult<ForumArticle> loadArticle(ArticleQueryVo vo);

    PageResult<ArticleCommentDto> getArticleComments(ArticleQueryVo vo);

    Boolean updateArticlesBoard(ArticleBoardVo vo);

}
