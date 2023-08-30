package com.easybbs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.easybbs.cconst.EHttpCode;
import com.easybbs.dto.ArticleCommentDto;
import com.easybbs.entity.ForumArticle;
import com.easybbs.entity.ForumComment;
import com.easybbs.enums.UpdateArticleCountTypeEnum;
import com.easybbs.enums.UserIntegralChangeTypeEnum;
import com.easybbs.exception.BusinessException;
import com.easybbs.mapper.ForumCommentMapper;
import com.easybbs.service.ForumArticleService;
import com.easybbs.mapper.ForumArticleMapper;
import com.easybbs.utils.SetPageUtils;
import com.easybbs.vo.ArticleBoardVo;
import com.easybbs.vo.ArticleQueryVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.easybbs.response.PageResult;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author Master-Z
 * @description 针对表【forum_article(文章信息)】的数据库操作Service实现
 * @createDate 2023-08-20 10:26:16
 */
@Service
public class ForumArticleServiceImpl extends ServiceImpl<ForumArticleMapper, ForumArticle> implements ForumArticleService {
    @Autowired
    ForumArticleMapper forumArticleMapper;
    @Autowired
    ForumCommentMapper forumCommentMapper;

    @Override
    public PageResult<ForumArticle> loadArticle(ArticleQueryVo vo) {
        QueryWrapper<ForumArticle> wrapper = new QueryWrapper<>();
        Page<ForumArticle> page = new Page<>(vo.getPageNo(), vo.getPageSize());

        if (!StringUtils.isBlank(vo.getTitleFuzzy())) {
            wrapper.likeRight("title", vo.getTitleFuzzy());
        }
        if (!StringUtils.isBlank(vo.getNickNameFuzzy())) {
            wrapper.likeRight("nick_name", vo.getTitleFuzzy());
        }
        if (Objects.nonNull(vo.getAttachmentType())) {
            wrapper.eq("attachment_type", vo.getAttachmentType());
        }
        if (Objects.nonNull(vo.getStatus())) {
            wrapper.eq("status", vo.getStatus());
        }

        forumArticleMapper.selectPage(page, wrapper);
        PageResult<ForumArticle> result = new PageResult<>();
        result.setList(page.getRecords());
        result.setPageNo(vo.getPageNo());
        result.setPageSize(page.getSize());
        result.setPageTotal(page.getPages());
        result.setTotalCount(page.getTotal());

        return result;
    }

    @Override
    public PageResult<ArticleCommentDto> getArticleComments(ArticleQueryVo vo) {
        QueryWrapper<ForumComment> wrapper = new QueryWrapper<>();
        Page<ForumComment> page = new Page<>(vo.getPageNo(), vo.getPageSize());

        wrapper.eq("article_id", vo.getArticleId());
        forumCommentMapper.selectPage(page, wrapper);

        PageResult<ArticleCommentDto> result = new PageResult<>();
        List<ArticleCommentDto> commentDtos = page.getRecords().stream().map(entity -> {
            ArticleCommentDto dto = new ArticleCommentDto();
            BeanUtils.copyProperties(entity, dto);
            return dto;
        }).collect(Collectors.toList());
        setChildComment(commentDtos);

        result.setList(commentDtos);
        result.setPageNo(vo.getPageNo());
        result.setPageSize(page.getSize());
        result.setPageTotal(page.getPages());
        result.setTotalCount(page.getTotal());
        return result;
    }

    @Override
    public PageResult<ArticleCommentDto> getComment(ArticleQueryVo vo) {
        QueryWrapper<ForumComment> wrapper = new QueryWrapper<>();
        if (!StringUtils.isBlank(vo.getContentFuzzy())) {
            wrapper.likeRight("content", vo.getContentFuzzy());
        }
        if (!StringUtils.isBlank(vo.getNickNameFuzzy())) {
            wrapper.likeRight("nick_name", vo.getNickNameFuzzy());
        }
        wrapper.eq("status", vo.getStatus());

        Page<ForumComment> page = new Page<>();
        forumCommentMapper.selectPage(page, wrapper);

        PageResult<ArticleCommentDto> pageResult = new PageResult<>();
        List<ArticleCommentDto> results = page.getRecords().stream().map(entity -> {
            ArticleCommentDto dto = new ArticleCommentDto();
            BeanUtils.copyProperties(entity, dto);
            return dto;
        }).collect(Collectors.toList());
        SetPageUtils.setPlusPageResult(vo.getPageNo(), results, page, pageResult);
        return pageResult;
    }

    @Override
    public Boolean updateArticlesBoard(ArticleBoardVo vo) {
        UpdateWrapper<ForumArticle> wrapper = new UpdateWrapper<>();
        wrapper.eq("article_id", vo.getArticleId());
        wrapper.set("board_id", vo.getBoardId());
        wrapper.set("p_board_id", vo.getPboardId());
        int result = forumArticleMapper.update(null, wrapper);
        return result == 1;
    }

    @Override
    public ForumArticle readArticle(String articleId) {
        ForumArticle forumArticle = forumArticleMapper.selectById(articleId);
        if (null == forumArticle) {
            throw new BusinessException(EHttpCode.CODE_404);
        }

        // 需要文章已经审核 status = 1
        if (forumArticle.getStatus().equals(1)) {
//            LambdaUpdateWrapper<ForumArticle> updateWrapper = new LambdaUpdateWrapper<>();
//            updateWrapper.eq(ForumArticle::getArticleId, articleId).setSql("read_count = read_count + 1");
//            forumArticleMapper.update(null, updateWrapper);
            forumArticleMapper.updateArticleCount(UpdateArticleCountTypeEnum.GOOD_COUNT.getType(),
                    UserIntegralChangeTypeEnum.REDUCE.getChangeType(),
                    articleId);
        }

        return forumArticle;
    }

    private void updateArticleCount() {

    }

    private void setChildComment(List<ArticleCommentDto> comments) {
        comments.forEach(comment -> {
            QueryWrapper<ForumComment> wrapper = new QueryWrapper<>();
            wrapper.eq("p_comment_id", comment.getCommentId());
            List<ArticleCommentDto> children = forumCommentMapper.selectList(wrapper).stream().map(entity -> {
                ArticleCommentDto dto = new ArticleCommentDto();
                BeanUtils.copyProperties(entity, dto);
                return dto;
            }).collect(Collectors.toList());
            comment.setChildren(children);
        });
    }
}




