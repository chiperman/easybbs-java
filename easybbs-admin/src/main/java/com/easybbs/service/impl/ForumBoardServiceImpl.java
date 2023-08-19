package com.easybbs.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.easybbs.entity.ForumBoard;
import com.easybbs.service.ForumBoardService;
import com.easybbs.mapper.ForumBoardMapper;
import org.springframework.stereotype.Service;

/**
* @author Master-Z
* @description 针对表【forum_board(文章板块信息)】的数据库操作Service实现
* @createDate 2023-08-19 16:20:54
*/
@Service
public class ForumBoardServiceImpl extends ServiceImpl<ForumBoardMapper, ForumBoard>
implements ForumBoardService{

}
