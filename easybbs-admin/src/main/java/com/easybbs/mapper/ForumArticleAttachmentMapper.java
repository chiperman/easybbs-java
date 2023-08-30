package com.easybbs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.easybbs.entity.ForumArticleAttachment;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 45083
 * @description 针对表【forum_article_attachment(文件信息)】的数据库操作Mapper
 * @createDate 2023-08-24 14:13:29
 * @Entity com.easybbs.entity.ForumArticleAttachment
 */
@Mapper
public interface ForumArticleAttachmentMapper extends BaseMapper<ForumArticleAttachment> {

}




