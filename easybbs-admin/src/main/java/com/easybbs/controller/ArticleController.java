package com.easybbs.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.easybbs.annotation.GlobalInterceptor;
import com.easybbs.cconst.Constants;
import com.easybbs.cconst.EHttpCode;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.easybbs.dto.*;
import com.easybbs.entity.ForumArticle;
import com.easybbs.entity.ForumArticleAttachment;
import com.easybbs.entity.LikeRecord;
import com.easybbs.enums.OperRecordOpTypeEnum;
import com.easybbs.exception.BusinessException;
import com.easybbs.service.ForumArticleAttachmentService;
import com.easybbs.service.ForumArticleService;
import com.easybbs.service.ForumCommentService;
import com.easybbs.service.LikeRecordService;
import com.easybbs.utils.SetResponseUtils;
import com.easybbs.vo.ArticleBoardVo;
import com.easybbs.vo.ArticleQueryVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.easybbs.response.MyResponse;
import com.easybbs.response.PageResult;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/forum")
public class ArticleController {
    @Autowired
    private ForumArticleService articleService;
    @Autowired
    private ForumCommentService commentService;
    @Autowired
    private ForumArticleAttachmentService articleAttachmentService;
    @Autowired
    private LikeRecordService likeRecordService;

    @RequestMapping("/loadArticle")
    public MyResponse<PageResult<ForumArticle>> getArticle(@RequestBody ArticleQueryVo vo) {
        PageResult<ForumArticle> result = articleService.loadArticle(vo);
        MyResponse<PageResult<ForumArticle>> response = new MyResponse<>();
        setResponseSuccess(response);
        response.setData(result);
        return response;
    }

    @RequestMapping("/getArticleDetail")
    public MyResponse<ForumArticleDetailDto> getArticleDetail(HttpSession session,
                                                              @Validated(ArticleQueryVo.GetArticleDetail.class)
                                                              @RequestBody ArticleQueryVo vo) {
        MyResponse<ForumArticleDetailDto> response = new MyResponse<>();
        String articleId = vo.getArticleId();
        ForumArticle forumArticle = articleService.readArticle(articleId);

        SessionWebUserDto sessionWebUserDto = (SessionWebUserDto) session.getAttribute(Constants.SESSION_KEY);

        Boolean canShowNoAudit = sessionWebUserDto != null && sessionWebUserDto.getUserId()
                .equals(forumArticle.getUserId()) || sessionWebUserDto.getIsAdmin();

        if (null == forumArticle || (forumArticle.getStatus().equals(1) && !canShowNoAudit) || forumArticle.getStatus()
                .equals(-1)) {
            throw new BusinessException(EHttpCode.CODE_404);
        }

        ForumArticleDetailDto articleDetail = new ForumArticleDetailDto();
        ForumArticleDto article = new ForumArticleDto();

        BeanUtils.copyProperties(forumArticle, article);
        articleDetail.setForumArticle(article);

        // 有附件
        if (forumArticle.getAttachmentType().equals(1)) {
            ForumArticleAttachment attachment = articleAttachmentService.getAttachmentByArticleId(vo.getArticleId());
            ForumArticleAttachmentDto attachmentDto = new ForumArticleAttachmentDto();
            BeanUtils.copyProperties(attachment, attachmentDto);
            articleDetail.setAttachment(attachmentDto);
        }

        // 是否已经点赞
        if (sessionWebUserDto != null) {
            QueryWrapper<LikeRecord> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("object_id", articleId)
                    .eq("user_id", sessionWebUserDto.getUserId())
                    .eq("op_type", OperRecordOpTypeEnum.ARTICLE_LIKE.getType());
            LikeRecord likeRecord = likeRecordService.getOne(queryWrapper);
            if (null != likeRecord) {
                articleDetail.setHaveLike(true);
            }
        }

        SetResponseUtils.setResponseSuccess(response, articleDetail);
        return response;
    }

    @RequestMapping("/doLike")
    public MyResponse<Object> doLike(HttpSession session,
                                     @Validated(ArticleQueryVo.DoLike.class)
                                     @RequestBody ArticleQueryVo vo) {
        MyResponse<Object> response = new MyResponse<>();
        SessionWebUserDto sessionWebUserDto = (SessionWebUserDto) session.getAttribute(Constants.SESSION_KEY);
        String articleId = vo.getArticleId();
        likeRecordService.doLike(articleId,
                sessionWebUserDto.getUserId(),
                sessionWebUserDto.getNickName(),
                OperRecordOpTypeEnum.ARTICLE_LIKE);
        SetResponseUtils.setResponseSuccess(response, null);
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

    @RequestMapping("/loadComment")
    public MyResponse<PageResult<ArticleCommentDto>> loadComment(@RequestBody ArticleQueryVo vo) {
        MyResponse<PageResult<ArticleCommentDto>> response = new MyResponse<>();

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
