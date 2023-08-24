package com.easybbs.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.easybbs.cconst.EHttpCode;
import com.easybbs.entity.ForumArticle;
import com.easybbs.enums.UpdateArticleCountTypeEnum;
import com.easybbs.enums.UserIntegralChangeTypeEnum;
import com.easybbs.exception.BusinessException;
import com.easybbs.mapper.ForumArticleMapper;
import com.easybbs.service.ForumArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 45083
 * @description 针对表【forum_article(文章信息)】的数据库操作Service实现
 * @createDate 2023-08-20 14:28:38
 */
@Service
public class ForumArticleServiceImpl extends ServiceImpl<ForumArticleMapper, ForumArticle> implements ForumArticleService {

    @Autowired
    ForumArticleMapper forumArticleMapper;

    @Override
    public ForumArticle readArticle(String articleId) {
        ForumArticle forumArticle = forumArticleMapper.selectById(articleId);
        if (null == forumArticle) {
            throw new BusinessException(EHttpCode.CODE_404);
        }

        // 需要文章已经审核 status = 1
        if (forumArticle.getStatus().equals(1)) {
            forumArticleMapper.updateArticleCount(UpdateArticleCountTypeEnum.READ_COUNT.getType(),
                    UserIntegralChangeTypeEnum.ADD.getChangeType(), articleId);
        }

        return forumArticle;
    }
}




