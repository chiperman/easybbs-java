package com.easybbs.controller;

import com.easybbs.cconst.EHttpCode;
import com.easybbs.dto.BoardData;
import com.easybbs.entity.ForumBoard;
import com.easybbs.service.ForumBoardService;
import com.easybbs.vo.BoardUpdateVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.easybbs.response.MyResponse;

import java.util.List;

@RestController
@RequestMapping("/board")
public class BoardController {
    @Autowired
    ForumBoardService forumBoardService;

    @RequestMapping("/loadBoard")
    public MyResponse<List<BoardData>> loadBoard() {
        List<BoardData> boards = forumBoardService.getBoard();
        MyResponse<List<BoardData>> response = new MyResponse<>();
        setResponseSuccess(response);
        response.setData(boards);
        return response;
    }

    @RequestMapping("/saveBoard")
    public MyResponse<BoardData> saveBoard(
            @Validated({BoardUpdateVo.Create.class, BoardUpdateVo.Update.class}) @RequestBody BoardUpdateVo vo) {
        ForumBoard entity = new ForumBoard();
        BoardData result = new BoardData();
        BeanUtils.copyProperties(vo, entity);
        forumBoardService.saveOrUpdate(entity);

        BeanUtils.copyProperties(entity, result);
        MyResponse<BoardData> response = new MyResponse<>();
        setResponseSuccess(response);
        response.setData(result);
        return response;
    }

    @RequestMapping("/delBoard")
    public MyResponse<BoardData> delBoard(@Validated(BoardUpdateVo.Delete.class) @RequestBody BoardUpdateVo vo) {
        forumBoardService.removeById(vo.getBoardId());
        MyResponse response = new MyResponse();
        setResponseSuccess(response);
        return response;
    }

    private void setResponseSuccess(MyResponse response) {
        response.setStatus(EHttpCode.SUCCESS.getStatus());
        response.setCode(EHttpCode.SUCCESS.getCode());
        response.setInfo(EHttpCode.SUCCESS.getInfo());
    }
}
