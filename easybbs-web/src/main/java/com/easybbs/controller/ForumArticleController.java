package com.easybbs.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.easybbs.annotation.GlobalInterceptor;
import com.easybbs.annotation.VerifyParam;
import com.easybbs.cconst.EHttpCode;
import com.easybbs.dto.SessionWebUserDto;
import com.easybbs.entity.ForumArticle;
import com.easybbs.entity.ForumArticleAttachment;
import com.easybbs.entity.LikeRecord;
import com.easybbs.enums.OperRecordOpTypeEnum;
import com.easybbs.exception.BusinessException;
import com.easybbs.mapper.ForumArticleAttachmentMapper;
import com.easybbs.mapper.LikeRecordMapper;
import com.easybbs.response.MyResponse;
import com.easybbs.service.ForumArticleService;
import com.easybbs.service.LikeRecordService;
import com.easybbs.utils.SetResponseUtils;
import com.easybbs.vo.ForumArticleAttachmentVO;
import com.easybbs.vo.ForumArticleDetailVO;
import com.easybbs.vo.ForumArticleVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/forum")
public class ForumArticleController {

    @Autowired
    ForumArticleService forumArticleService;

    @Autowired
    BaseController baseController;

    @Autowired
    ForumArticleAttachmentMapper forumArticleAttachmentMapper;

    @Autowired
    LikeRecordService likeRecordService;

    @Autowired
    LikeRecordMapper likeRecordMapper;

    @RequestMapping("/getArticleDetail")
    @GlobalInterceptor(checkParams = true)
    public MyResponse<ForumArticleVO> getArticleDetail(HttpSession session,
                                                       @VerifyParam(required = true) String articleId) {
        MyResponse<ForumArticleVO> response = new MyResponse<>();
        ForumArticle forumArticle = forumArticleService.readArticle(articleId);

        SessionWebUserDto sessionWebUserDto = baseController.getUserInfoFromSession(session);

        Boolean canShowNoAudit =
                sessionWebUserDto != null && sessionWebUserDto.getUserId().equals(forumArticle.getUserId()) || sessionWebUserDto.getIsAdmin();

        if (null == forumArticle || (forumArticle.getStatus().equals(1) && !canShowNoAudit) || forumArticle.getStatus().equals(-1)) {
            throw new BusinessException(EHttpCode.CODE_404);
        }

        ForumArticleDetailVO forumArticleDetailVO = new ForumArticleDetailVO();
        ForumArticleVO forumArticleVO = new ForumArticleVO();

        BeanUtils.copyProperties(forumArticle, forumArticleVO);
        forumArticleDetailVO.setForumArticleVO(forumArticleVO);

        // 有附件
        if (forumArticle.getAttachmentType().equals(1)) {
            QueryWrapper<ForumArticleAttachment> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("article_id", articleId);
            List<ForumArticleAttachment> list = forumArticleAttachmentMapper.selectList(queryWrapper);
            ForumArticleAttachmentVO forumArticleAttachmentVO = new ForumArticleAttachmentVO();
            BeanUtils.copyProperties(list.get(0), forumArticleAttachmentVO);
            forumArticleDetailVO.setForumArticleAttachmentVO(forumArticleAttachmentVO);
        }

        // 是否已经点赞
        if (sessionWebUserDto != null) {

            QueryWrapper<LikeRecord> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("object_id", articleId).eq("user_id", sessionWebUserDto.getUserId()).eq("op_type",
                    OperRecordOpTypeEnum.ARTICLE_LIKE.getType());
            LikeRecord likeRecord = likeRecordService.getOne(queryWrapper);
            if (null != likeRecord) {
                forumArticleDetailVO.setHaveLike(true);
            }
        }


        SetResponseUtils.setResponseSuccess(response, forumArticleDetailVO);
        return response;
    }
}
