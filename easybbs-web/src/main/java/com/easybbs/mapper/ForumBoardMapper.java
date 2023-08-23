package com.easybbs.mapper;

import com.easybbs.dto.ForumBoardDto;
import com.easybbs.entity.ForumBoard;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author 45083
 * @description 针对表【forum_board(文章板块信息)】的数据库操作Mapper
 * @createDate 2023-08-19 16:59:37
 * @Entity com.easybbs.entity.ForumBoard
 */
@Mapper
public interface ForumBoardMapper {

    int deleteByPrimaryKey(Long id);

    int insert(ForumBoard record);

    int insertSelective(ForumBoard record);

    ForumBoard selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ForumBoard record);

    int updateByPrimaryKey(ForumBoard record);

    List<ForumBoardDto> selectAllBoard();

}
