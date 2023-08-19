package com.easybbs.controller;

import cconst.EHttpCode;
import com.easybbs.mapper.ForumBoardMapper;
import com.easybbs.vo.ForumBoardResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import response.MyResponse;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/board")
public class ForumBoardController {
    @Autowired
    ForumBoardMapper forumBoardMapper;


    @RequestMapping(value = "/loadBoard", method = RequestMethod.POST)
    public MyResponse<List<ForumBoardResponseVO>> getAllBoard(HttpServletRequest request) {
        // 创建一个用于存放板块数据的List
        List<ForumBoardResponseVO> boardList = forumBoardMapper.selectAllBoard();


        MyResponse<List<ForumBoardResponseVO>> response = new MyResponse<>();
        response.setCode(EHttpCode.SUCCESS.getCode());
        response.setInfo(EHttpCode.SUCCESS.getInfo());
        response.setStatus(EHttpCode.SUCCESS.getStatus());
        response.setData(boardList);
        return response;
    }


}
