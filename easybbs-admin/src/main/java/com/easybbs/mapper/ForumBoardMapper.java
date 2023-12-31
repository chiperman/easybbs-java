package com.easybbs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.easybbs.entity.ForumBoard;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Master-Z
 * @description 针对表【forum_board(文章板块信息)】的数据库操作Mapper
 * @createDate 2023-08-19 16:20:54
 * @Entity com.easybbs.entity.ForumBoard
 */
@Mapper
public interface ForumBoardMapper extends BaseMapper<ForumBoard> {

}
