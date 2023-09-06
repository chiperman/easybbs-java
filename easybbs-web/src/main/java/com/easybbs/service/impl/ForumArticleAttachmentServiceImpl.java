package com.easybbs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.easybbs.dto.SessionWebUserDto;
import com.easybbs.entity.*;
import com.easybbs.enums.MessageStatusEnum;
import com.easybbs.enums.MessageTypeEnum;
import com.easybbs.enums.UserIntegralChangeTypeEnum;
import com.easybbs.enums.UserIntegralOperTypeEnum;
import com.easybbs.exception.BusinessException;
import com.easybbs.mapper.ForumArticleAttachmentDownloadMapper;
import com.easybbs.mapper.ForumArticleAttachmentMapper;
import com.easybbs.mapper.UserMessageMapper;
import com.easybbs.service.ForumArticleAttachmentService;
import com.easybbs.service.ForumArticleService;
import com.easybbs.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @author 45083
 * @description 针对表【forum_article_attachment(文件信息)】的数据库操作Service实现
 * @createDate 2023-08-24 14:13:29
 */
@Service
public class ForumArticleAttachmentServiceImpl extends ServiceImpl<ForumArticleAttachmentMapper,
        ForumArticleAttachment> implements ForumArticleAttachmentService {

    @Autowired
    ForumArticleAttachmentMapper forumArticleAttachmentMapper;

    @Autowired
    ForumArticleAttachmentDownloadMapper forumArticleAttachmentDownloadMapper;

    @Autowired
    UserInfoService userInfoService;

    @Autowired
    ForumArticleService forumArticleService;

    @Autowired
    UserMessageMapper userMessageMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ForumArticleAttachment downloadAttachment(String fileId, SessionWebUserDto sessionWebUserDto) {
        ForumArticleAttachment forumArticleAttachment = forumArticleAttachmentMapper.selectById(fileId);
        if (null == forumArticleAttachment) {
            throw new BusinessException("附件不存在");
        }

        ForumArticleAttachmentDownload download = null;
        if (forumArticleAttachment.getIntegral() > 0 && !sessionWebUserDto.getUserId()
                .equals(forumArticleAttachment.getUserId())) {

            QueryWrapper<ForumArticleAttachmentDownload> queryWrapper = new QueryWrapper();
            queryWrapper.eq("file_id", fileId).eq("user_id", sessionWebUserDto.getUserId());
            download = forumArticleAttachmentDownloadMapper.selectOne(queryWrapper);
            if (null == download) {
                UserInfo userInfo = userInfoService.getById(sessionWebUserDto.getUserId());
                if (userInfo.getCurrentIntegral() - forumArticleAttachment.getIntegral() < 0) {
                    throw new BusinessException("积分不够");
                }
            }
        }

        ForumArticleAttachmentDownload updateDownload = new ForumArticleAttachmentDownload();
        updateDownload.setArticleId(forumArticleAttachment.getArticleId());
        updateDownload.setFileId(fileId);
        updateDownload.setUserId(sessionWebUserDto.getUserId());
        updateDownload.setDownloadCount(1);
        this.forumArticleAttachmentDownloadMapper.insertOrUpdate(updateDownload);

        this.forumArticleAttachmentMapper.updateDownloadCount(fileId);

        if (sessionWebUserDto.getUserId().equals(forumArticleAttachment.getUserId()) || download != null) {
            return forumArticleAttachment;
        }

        // 扣除下载人积分
        userInfoService.updateUserIntegral(sessionWebUserDto.getUserId(),
                UserIntegralOperTypeEnum.USER_DOWNLOAD_ATTACHMENT,
                UserIntegralChangeTypeEnum.REDUCE.getChangeType(),
                forumArticleAttachment.getIntegral());

        // 给附件提供者增加积分
        userInfoService.updateUserIntegral(forumArticleAttachment.getUserId(),
                UserIntegralOperTypeEnum.DOWNLOAD_ATTACHMENT,
                UserIntegralChangeTypeEnum.ADD.getChangeType(),
                forumArticleAttachment.getIntegral());

        // 记录消息
        ForumArticle forumArticle = forumArticleService.getBaseMapper()
                .selectById(forumArticleAttachment.getArticleId());

        UserMessage userMessage = new UserMessage();
        userMessage.setMessageType(MessageTypeEnum.DOWNLOAD_ATTACHMENT.getType());
        userMessage.setCreateTime(new Date());
        userMessage.setArticleId(forumArticle.getArticleId());
        userMessage.setArticleTitle(forumArticle.getTitle());
        userMessage.setReceivedUserId(forumArticle.getUserId());
        userMessage.setCommentId(0);
        userMessage.setSendUserId(Long.valueOf(sessionWebUserDto.getUserId()));
        userMessage.setSendNickName(sessionWebUserDto.getNickName());
        userMessage.setStatus(MessageStatusEnum.NO_READ.getStatus());

        userMessageMapper.insert(userMessage);

        return forumArticleAttachment;
    }
}




