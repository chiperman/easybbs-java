package com.easybbs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.easybbs.dto.UserArticleCount;
import com.easybbs.entity.ForumArticle;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author 45083
 * @description 针对表【forum_article(文章信息)】的数据库操作Mapper
 * @createDate 2023-08-20 14:28:38
 * @Entity com.easybbs.entity.ForumArticle
 */
@Mapper
public interface ForumArticleMapper extends BaseMapper<ForumArticle> {
    UserArticleCount getUserArticleCount(@Param("userId") Long userId);

}




