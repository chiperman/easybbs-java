package com.easybbs.mapper;

import com.easybbs.entity.ForumComment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author Master-Z
* @description 针对表【forum_comment(评论)】的数据库操作Mapper
* @createDate 2023-08-20 12:01:58
* @Entity com.easybbs.entity.ForumComment
*/
@Mapper
public interface ForumCommentMapper extends BaseMapper<ForumComment> {

}




