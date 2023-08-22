package com.easybbs.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.easybbs.dto.BoardData;
import com.easybbs.entity.ForumBoard;
import com.easybbs.service.ForumBoardService;
import com.easybbs.mapper.ForumBoardMapper;
import com.google.common.collect.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
* @author Master-Z
* @description 针对表【forum_board(文章板块信息)】的数据库操作Service实现
* @createDate 2023-08-19 16:20:54
*/
@Service
public class ForumBoardServiceImpl extends ServiceImpl<ForumBoardMapper, ForumBoard> implements ForumBoardService{

    @Autowired
    private ForumBoardMapper forumBoardMapper;
    @Override
    public List<BoardData> getBoard() {
        QueryWrapper<ForumBoard> wrapper = new QueryWrapper<>();
        wrapper.eq("p_board_id", 0);
        List<ForumBoard> rootEntity = forumBoardMapper.selectList(wrapper);
        List<BoardData> result = getSubBoard(rootEntity);
        return result;
    }

    private List<BoardData> getSubBoard(List<ForumBoard> roots) {
        List<BoardData> result = Lists.newArrayList();
        roots.forEach(root -> {
            BoardData rootDto = new BoardData();
            BeanUtils.copyProperties(root, rootDto);
            QueryWrapper<ForumBoard> wrapper = new QueryWrapper<>();
            wrapper.eq("p_board_id", root.getBoardId());
            List<ForumBoard> children = forumBoardMapper.selectList(wrapper);
            List<BoardData> sub = children.stream().map(child -> {
                BoardData data = new BoardData();
                BeanUtils.copyProperties(child, data);
                return data;
            }).collect(Collectors.toList());
            rootDto.setChildren(sub);
            result.add(rootDto);
        });
        return result;
    }
}
