package com.easybbs.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.easybbs.annotation.GlobalInterceptor;
import com.easybbs.cconst.Constants;
import com.easybbs.cconst.EHttpCode;
import com.easybbs.config.WebConfig;
import com.easybbs.dto.ArticleIdDto;
import com.easybbs.dto.SessionWebUserDto;
import com.easybbs.dto.UserDownloadInfoDto;
import com.easybbs.entity.*;
import com.easybbs.enums.OperRecordOpTypeEnum;
import com.easybbs.exception.BusinessException;
import com.easybbs.mapper.ForumArticleAttachmentMapper;
import com.easybbs.mapper.LikeRecordMapper;
import com.easybbs.response.MyResponse;
import com.easybbs.service.*;
import com.easybbs.utils.SetResponseUtils;
import com.easybbs.vo.ForumArticleAttachmentVO;
import com.easybbs.vo.ForumArticleDetailVO;
import com.easybbs.vo.ForumArticleVO;
import com.easybbs.vo.UserDownloadInfoVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.URLEncoder;
import java.util.List;

@RestController
@RequestMapping("/forum")
public class ForumArticleController {

    private static final Logger logger = LoggerFactory.getLogger(ForumArticleController.class);
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

    @Autowired
    UserInfoService userInfoService;

    @Autowired
    ForumArticleAttachmentDownloadService forumArticleAttachmentDownloadService;

    @Autowired
    ForumArticleAttachmentService forumArticleAttachmentService;

    @Autowired
    WebConfig webConfig;

    @RequestMapping("/getArticleDetail")
    @GlobalInterceptor(checkParams = true)
    public MyResponse<ForumArticleVO> getArticleDetail(HttpSession session, @RequestBody ArticleIdDto articleIdDto) {
        MyResponse<ForumArticleVO> response = new MyResponse<>();
        String articleId = articleIdDto.getArticleId();
        ForumArticle forumArticle = forumArticleService.readArticle(articleId);

        SessionWebUserDto sessionWebUserDto = baseController.getUserInfoFromSession(session);

        Boolean canShowNoAudit = sessionWebUserDto != null && sessionWebUserDto.getUserId()
                .equals(forumArticle.getUserId()) || sessionWebUserDto.getIsAdmin();

        if (null == forumArticle || (forumArticle.getStatus().equals(1) && !canShowNoAudit) || forumArticle.getStatus()
                .equals(-1)) {
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
            queryWrapper.eq("object_id", articleId)
                    .eq("user_id", sessionWebUserDto.getUserId())
                    .eq("op_type", OperRecordOpTypeEnum.ARTICLE_LIKE.getType());
            LikeRecord likeRecord = likeRecordService.getOne(queryWrapper);
            if (null != likeRecord) {
                forumArticleDetailVO.setHaveLike(true);
            }
        }


        SetResponseUtils.setResponseSuccess(response, forumArticleDetailVO);
        return response;
    }


    @RequestMapping("/doLike")
    @GlobalInterceptor(checkLogin = true, checkParams = true)
    public MyResponse<Object> doLike(HttpSession session, @RequestBody ArticleIdDto articleIdDto) {
        MyResponse<Object> response = new MyResponse<>();
        SessionWebUserDto sessionWebUserDto = baseController.getUserInfoFromSession(session);
        String articleId = articleIdDto.getArticleId();
        likeRecordService.doLike(articleId,
                sessionWebUserDto.getUserId(),
                sessionWebUserDto.getNickName(),
                OperRecordOpTypeEnum.ARTICLE_LIKE);
        SetResponseUtils.setResponseSuccess(response, null);
        return response;
    }

    @RequestMapping("/getUserDownloadInfo")
    @GlobalInterceptor(checkLogin = true, checkParams = true)
    public MyResponse<Object> getUserDownloadInfo(HttpSession session,
                                                  @RequestBody UserDownloadInfoDto userDownloadInfoDto) {
        MyResponse<Object> response = new MyResponse<>();
        String fileId = userDownloadInfoDto.getFileId();

        SessionWebUserDto sessionWebUserDto = baseController.getUserInfoFromSession(session);

        String userId = sessionWebUserDto.getUserId();

        UserInfo userInfo = userInfoService.getById(userId);

        UserDownloadInfoVO userDownloadInfoVO = new UserDownloadInfoVO();
        userDownloadInfoVO.setUserIntegral(userInfo.getCurrentIntegral());

        QueryWrapper<ForumArticleAttachmentDownload> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("file_id", fileId).eq("user_id", userId);
        ForumArticleAttachmentDownload attachmentDownload = forumArticleAttachmentDownloadService.getOne(queryWrapper);
        if (null != attachmentDownload) {
            userDownloadInfoVO.setHaveDownload(true);
        }

        SetResponseUtils.setResponseSuccess(response, userDownloadInfoVO);
        return response;
    }

    @RequestMapping("/attachmentDownload")
    @GlobalInterceptor(checkLogin = true, checkParams = true)
    public void attachmentDownload(HttpSession session,
                                   HttpServletRequest request,
                                   HttpServletResponse response,
                                   @RequestBody UserDownloadInfoDto userDownloadInfoDto) {

        String fileId = userDownloadInfoDto.getFileId();


        ForumArticleAttachment attachment = forumArticleAttachmentService.downloadAttachment(fileId,
                baseController.getUserInfoFromSession(session));
        InputStream in = null;
        OutputStream out = null;
        String downloadFineName = attachment.getFileName();
        String filePath =
                webConfig.getProjectFolder() + Constants.FILE_FOLDER_FILE + Constants.FILE_FOLDER_ATTACHMENT + attachment.getFilePath();
        File file = new File(filePath);
        try {
            in = new FileInputStream(file);
            out = response.getOutputStream();
            if (request.getHeader("User-Agent").toLowerCase().indexOf("msie") > 0) {
                downloadFineName = URLEncoder.encode(downloadFineName, "UTF-8");
            } else {
                downloadFineName = new String(downloadFineName.getBytes("UTF-8"), "ISO8859-1");
            }
            response.setHeader("Content-Disposition", "attachment;filename=\"" + downloadFineName + "\"");
            byte[] byteData = new byte[1024];
            int len = 0;
            while ((len = in.read(byteData)) != -1) {
                out.write(byteData, 0, len);
            }
            out.flush();
        } catch (Exception e) {
            logger.error("下载异常", e);
            throw new BusinessException("下载失败");
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                logger.error("IO异常", e);
            }
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                logger.error("IO异常", e);
            }
        }
    }

}
