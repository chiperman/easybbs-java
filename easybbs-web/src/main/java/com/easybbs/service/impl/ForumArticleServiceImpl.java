package com.easybbs.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.easybbs.entity.ForumArticle;
import com.easybbs.service.ForumArticleService;
import com.easybbs.mapper.ForumArticleMapper;
import org.springframework.stereotype.Service;

/**
* @author 45083
* @description 针对表【forum_article(文章信息)】的数据库操作Service实现
* @createDate 2023-08-20 14:28:38
*/
@Service
public class ForumArticleServiceImpl extends ServiceImpl<ForumArticleMapper, ForumArticle>
    implements ForumArticleService{

}




