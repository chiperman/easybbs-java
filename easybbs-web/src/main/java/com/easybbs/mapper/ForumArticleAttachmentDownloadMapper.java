package com.easybbs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.easybbs.entity.ForumArticleAttachmentDownload;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 45083
 * @description 针对表【forum_article_attachment_download(用户附件下载)】的数据库操作Mapper
 * @createDate 2023-09-06 14:31:07
 * @Entity com.easybbs.entity.ForumArticleAttachmentDownload
 */

@Mapper
public interface ForumArticleAttachmentDownloadMapper extends BaseMapper<ForumArticleAttachmentDownload> {

    void insertOrUpdate(ForumArticleAttachmentDownload updateDownload);
}




