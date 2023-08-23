package com.easybbs.controller;

import com.easybbs.cconst.EHttpCode;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.easybbs.dto.ArticleCommentDto;
import com.easybbs.entity.ForumArticle;
import com.easybbs.service.ForumArticleService;
import com.easybbs.service.ForumCommentService;
import com.easybbs.vo.ArticleBoardVo;
import com.easybbs.vo.ArticleQueryVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.easybbs.response.MyResponse;
import com.easybbs.response.PageResult;

@RestController
@RequestMapping("/forum")
public class ArticleController {
    @Autowired
    private ForumArticleService articleService;
    @Autowired
    private ForumCommentService commentService;

    @RequestMapping("/loadArticle")
    public MyResponse<PageResult<ForumArticle>> getArticle(@RequestBody ArticleQueryVo vo) {
        PageResult<ForumArticle> result = articleService.loadArticle(vo);
        MyResponse<PageResult<ForumArticle>> response = new MyResponse<>();
        setResponseSuccess(response);
        response.setData(result);
        return response;
    }

    @RequestMapping("/delArticle")
    public MyResponse delArticle(@Validated(ArticleQueryVo.DeleteArticle.class) @RequestBody ArticleQueryVo vo) {
        boolean result = articleService.removeById(vo.getArticleId());
        MyResponse response = new MyResponse<>();

        if (!result) {
            setResponseFail(response, "文章ID不存在");
        } else {
            setResponseSuccess(response);
        }

        return response;
    }

    @RequestMapping("/updateBoard")
    public MyResponse updateBoard(@RequestBody ArticleBoardVo vo) {
        Boolean success = articleService.updateArticlesBoard(vo);
        MyResponse response = new MyResponse();
        if (!success) {
            response.setStatus(EHttpCode.FAIL.getStatus());
            response.setCode(EHttpCode.FAIL.getCode());
            response.setInfo(EHttpCode.FAIL.getInfo());
        } else {
            response.setStatus(EHttpCode.SUCCESS.getStatus());
            response.setCode(EHttpCode.SUCCESS.getCode());
            response.setInfo(EHttpCode.SUCCESS.getInfo());
        }
        return response;
    }

    @RequestMapping("/topArticle")
    public MyResponse topArticle(@Validated(ArticleQueryVo.TopArticle.class) @RequestBody ArticleQueryVo vo) {
        UpdateWrapper<ForumArticle> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("article_id", vo.getArticleId());
        updateWrapper.set("top_type", vo.getTopType());
        boolean result = articleService.update(null, updateWrapper);
        MyResponse<PageResult<ForumArticle>> response = new MyResponse<>();

        if (!result) {
            setResponseFail(response, "文章ID不存在");
        } else {
            setResponseSuccess(response);
        }

        return response;
    }

    @RequestMapping("/loadComment4Article")
    public MyResponse<PageResult<ArticleCommentDto>> loadComment4Article(@RequestBody ArticleQueryVo vo) {
        MyResponse<PageResult<ArticleCommentDto>> response = new MyResponse<>();
        if (StringUtils.isBlank(vo.getArticleId())) {
            setResponseFail(response, "文章ID为空");
            return response;
        }

        PageResult<ArticleCommentDto> result = articleService.getArticleComments(vo);
        setResponseSuccess(response);
        response.setData(result);
        return response;
    }

    @RequestMapping("/delComment")
    public MyResponse delComment(@Validated(ArticleQueryVo.DelComment.class) @RequestBody ArticleQueryVo vo) {
        boolean result = commentService.removeById(vo.getCommentId());
        MyResponse response = new MyResponse<>();

        if (!result) {
            setResponseFail(response, "评论ID不存在");
        } else {
            setResponseSuccess(response);
        }

        return response;
    }

    private void setResponseSuccess(MyResponse response) {
        response.setStatus(EHttpCode.SUCCESS.getStatus());
        response.setCode(EHttpCode.SUCCESS.getCode());
        response.setInfo(EHttpCode.SUCCESS.getInfo());
    }

    private void setResponseFail(MyResponse response, String info) {
        response.setStatus(EHttpCode.FAIL.getStatus());
        response.setCode(EHttpCode.FAIL.getCode());
        response.setInfo(info);
    }
}
