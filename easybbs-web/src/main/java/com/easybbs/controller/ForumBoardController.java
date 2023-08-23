package com.easybbs.controller;

import com.easybbs.mapper.ForumBoardMapper;
import com.easybbs.vo.ForumBoardResponseVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.easybbs.response.MyResponse;
import com.easybbs.utils.SetResponseUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/board")
public class ForumBoardController {
    @Autowired
    ForumBoardMapper forumBoardMapper;

    private static final Logger log = LoggerFactory.getLogger(ForumBoardController.class);


    @RequestMapping(value = "/loadBoard", method = RequestMethod.POST)
    public MyResponse<List<ForumBoardResponseVO>> getAllBoard(HttpServletRequest request) {
        // 创建一个用于存放板块数据的List
        List<ForumBoardResponseVO> boardList = forumBoardMapper.selectAllBoard();
        MyResponse<List<ForumBoardResponseVO>> response = new MyResponse<>();
        getChildrenBoard(boardList, 0L);
        SetResponseUtils.setResponseSuccess(response, boardList);
        return response;
    }

    private List<ForumBoardResponseVO> getChildrenBoard(List<ForumBoardResponseVO> data, Long pid) {
        List<ForumBoardResponseVO> children = new ArrayList<>();
        for (ForumBoardResponseVO m : data) {
            if (m.getPBoardId().equals(pid)) {
                m.setChildren(getChildrenBoard(data, m.getBoardId()));
                children.add(m);
            }
        }
        return children;
    }


}
