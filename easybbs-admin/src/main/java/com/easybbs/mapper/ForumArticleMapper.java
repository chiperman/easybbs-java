package com.easybbs.mapper;

import com.easybbs.entity.ForumArticle;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author Master-Z
* @description 针对表【forum_article(文章信息)】的数据库操作Mapper
* @createDate 2023-08-20 10:26:16
* @Entity com.easybbs.entity.ForumArticle
*/
@Mapper
public interface ForumArticleMapper extends BaseMapper<ForumArticle> {

}




