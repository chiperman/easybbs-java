package com.easybbs.service;

import com.easybbs.dto.BoardData;
import com.easybbs.entity.ForumBoard;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author Master-Z
* @description 针对表【forum_board(文章板块信息)】的数据库操作Service
* @createDate 2023-08-19 16:20:54
*/
public interface ForumBoardService extends IService<ForumBoard> {

    public List<BoardData> getBoard();
}
