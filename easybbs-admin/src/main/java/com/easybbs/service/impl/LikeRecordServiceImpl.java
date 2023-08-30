package com.easybbs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.easybbs.cconst.Constants;
import com.easybbs.entity.ForumArticle;
import com.easybbs.entity.LikeRecord;
import com.easybbs.entity.UserMessage;
import com.easybbs.enums.*;
import com.easybbs.exception.BusinessException;
import com.easybbs.mapper.ForumArticleMapper;
import com.easybbs.mapper.LikeRecordMapper;
import com.easybbs.mapper.UserMessageMapper;
import com.easybbs.service.LikeRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @author 45083
 * @description 针对表【like_record(点赞记录)】的数据库操作Service实现
 * @createDate 2023-08-21 14:40:27
 */
@Service
public class LikeRecordServiceImpl extends ServiceImpl<LikeRecordMapper, LikeRecord> implements LikeRecordService {

    @Autowired
    UserMessageMapper userMessageMapper;

    @Autowired
    LikeRecordMapper likeRecordMapper;

    @Autowired
    ForumArticleMapper forumArticleMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void doLike(String objectId, String userId, String nickName, OperRecordOpTypeEnum opTypeEnum) {
        UserMessage userMessage = new UserMessage();
        userMessage.setCreateTime(new Date());
        switch (opTypeEnum) {
            case ARTICLE_LIKE:
                ForumArticle forumArticle = forumArticleMapper.selectById(objectId);
                if (forumArticle == null) {
                    throw new BusinessException("文章不存在");
                }
                articleLike(forumArticle, objectId, userId, opTypeEnum);
                userMessage.setArticleId(objectId);
                userMessage.setArticleTitle(forumArticle.getTitle());
                userMessage.setMessageType(MessageTypeEnum.ARTICLE_LIKE.getType());
                userMessage.setCommentId(Constants.ZERO);
                userMessage.setReceivedUserId(forumArticle.getUserId());
                break;
            case COMMENT_LIKE:
                break;
        }
        userMessage.setSendUserId(Long.valueOf(userId));
        userMessage.setSendNickName(nickName);
        userMessage.setStatus(MessageStatusEnum.NO_READ.getStatus());
        // 已经点过赞
        if (!userId.equals(userMessage.getReceivedUserId())) {
            QueryWrapper<UserMessage> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("article_id", userMessage.getArticleId())
                    .eq("comment_id", userMessage.getCommentId())
                    .eq("send_user_id", userMessage.getSendUserId())
                    .eq("message_type", userMessage.getMessageType());

            UserMessage dbInfo = userMessageMapper.selectOne(queryWrapper);
            if (null == dbInfo) {
                userMessageMapper.insert(userMessage);
            }
        }
    }

    public LikeRecord articleLike(ForumArticle forumArticle,
                                  String objId,
                                  String userId,
                                  OperRecordOpTypeEnum opTypeEnum) {

        QueryWrapper<LikeRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("object_id", objId).eq("user_id", userId).eq("op_type", opTypeEnum.getType());
        LikeRecord record = likeRecordMapper.selectOne(queryWrapper);
        if (null != record) {
            likeRecordMapper.delete(queryWrapper);
            forumArticleMapper.updateArticleCount(UpdateArticleCountTypeEnum.GOOD_COUNT.getType(),
                    UserIntegralChangeTypeEnum.REDUCE.getChangeType(),
                    objId);
        } else {
            LikeRecord likeRecord = new LikeRecord();
            likeRecord.setObjectId(objId);
            likeRecord.setUserId(userId);
            likeRecord.setOpType(opTypeEnum.getType());
            likeRecord.setCreateTime(new Date());
            likeRecord.setAuthorUserId(String.valueOf(forumArticle.getUserId()));
            likeRecordMapper.insert(likeRecord);
            forumArticleMapper.updateArticleCount(UpdateArticleCountTypeEnum.GOOD_COUNT.getType(),
                    UserIntegralChangeTypeEnum.ADD.getChangeType(),
                    objId);
        }
        return record;
    }
}




