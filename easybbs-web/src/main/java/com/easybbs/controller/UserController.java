package com.easybbs.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.easybbs.annotation.GlobalInterceptor;
import com.easybbs.dto.LoadUserArticleDto;
import com.easybbs.dto.SessionWebUserDto;
import com.easybbs.dto.UserArticleCount;
import com.easybbs.dto.UserIntegralDto;
import com.easybbs.entity.ForumArticle;
import com.easybbs.entity.LikeRecord;
import com.easybbs.entity.UserInfo;
import com.easybbs.entity.UserIntegralRecord;
import com.easybbs.mapper.ForumArticleMapper;
import com.easybbs.mapper.LikeRecordMapper;
import com.easybbs.request.UserIdRequest;
import com.easybbs.response.MyResponse;
import com.easybbs.response.PageResult;
import com.easybbs.service.UserInfoService;
import com.easybbs.service.UserIntegralRecordService;
import com.easybbs.utils.SetPageUtils;
import com.easybbs.utils.SetResponseUtils;
import com.easybbs.vo.ForumArticleVO;
import com.easybbs.vo.UserInfoResponseVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/ucenter")
public class UserController {

    @Autowired
    UserInfoService userInfoService;

    @Autowired
    ForumArticleMapper forumArticleMapper;

    @Autowired
    UserIntegralRecordService userIntegralRecordService;

    @Autowired
    LikeRecordMapper likeRecordMapper;

    @Autowired
    BaseController baseController;


    /**
     * 获取用户信息
     *
     * @param userIdRequest
     *
     * @return
     */
    @RequestMapping(value = "/getUserInfo", method = RequestMethod.POST)
    @GlobalInterceptor(checkParams = true)
    public MyResponse<UserInfoResponseVO> getUserInfo(@RequestBody UserIdRequest userIdRequest) {
        MyResponse<UserInfoResponseVO> response = new MyResponse<>();
        Long userId = userIdRequest.getUserId();
        UserInfoResponseVO userInfoResponseVO = new UserInfoResponseVO();
        // 根据 userId 获得用户信息
        UserInfo userInfo = getUserInfo(userId);

        // 将 userInfo 复制到 userInfoResponseVO
        BeanUtils.copyProperties(userInfo, userInfoResponseVO);

        UserArticleCount userArticleCount = getUserArticleCount(userId);
        userInfoResponseVO.setPostCount(userArticleCount.getPostCount());
        userInfoResponseVO.setLikeCount(userArticleCount.getLikeCount());

        SetResponseUtils.setResponseSuccess(response, userInfoResponseVO);
        return response;
    }

    /**
     * 获取登录用户信息
     *
     * @param session
     *
     * @return
     */
    @RequestMapping(value = "/getLoginUserInfo")
    @GlobalInterceptor(checkParams = true)
    public MyResponse<SessionWebUserDto> getUserInfo(HttpSession session) {
        MyResponse<SessionWebUserDto> response = new MyResponse<>();
        SessionWebUserDto sessionWebUserDto = baseController.getUserInfoFromSession(session);
        SetResponseUtils.setResponseSuccess(response, sessionWebUserDto);
        return response;
    }

    /**
     * 获取登录用户信息
     *
     * @param session
     *
     * @return
     */
    @RequestMapping(value = "/logout")
    @GlobalInterceptor(checkParams = true)
    public MyResponse<SessionWebUserDto> logout(HttpSession session) {
        MyResponse<SessionWebUserDto> response = new MyResponse<>();
        session.invalidate();
        SetResponseUtils.setResponseSuccess(response, null);
        return response;
    }


    private UserInfo getUserInfo(Long userId) {
        return userInfoService.getById(userId);
    }


    /**
     * 获得用户的发帖数和点赞数
     *
     * @param userId
     *
     * @return
     */
    private UserArticleCount getUserArticleCount(Long userId) {
        return forumArticleMapper.getUserArticleCount(userId);
    }


    /**
     * 积分记录
     *
     * @return
     */
    @RequestMapping(value = "/loadUserIntegralRecord", method = RequestMethod.POST)
    // TODO: 是否应该根据用户ID获得相对的记录
    public MyResponse<PageResult<List<UserIntegralRecord>>> loadUserIntegralRecord() {
        UserIntegralDto userIntegralDto = new UserIntegralDto();
        PageHelper.startPage(userIntegralDto.getPageNo(), userIntegralDto.getPageSize());

        List<UserIntegralRecord> list = userIntegralRecordService.list();

        PageInfo<UserIntegralRecord> page = new PageInfo<>(list);
        PageResult<List<UserIntegralRecord>> result = new PageResult<>();

        SetPageUtils.setPageResult(page, result);

        MyResponse<PageResult<List<UserIntegralRecord>>> response = new MyResponse<>();
        SetResponseUtils.setResponseSuccess(response, result);
        return response;
    }


    // @RequestMapping(value = "/updateUserInfo", method = RequestMethod.POST)
    // public MyResponse updateUserInfo(@RequestBody UserInfoUpdateVO vo) {
    //     System.out.println(vo.getSex());
    //     return new MyResponse<>();
    // }


    /**
     * 1. 先根据 vo 里面的 userId 和 type 到 like_record 找到对应的数据 List
     * 2. 遍历 List 找到 object_id 然后再去 forum_article 找到对应的文章
     * 3. 存入最后的结果中
     *
     * @param dto
     *
     * @return
     */
    @RequestMapping(value = "/loadUserArticle", method = RequestMethod.POST)
    @GlobalInterceptor(checkParams = true)
    public MyResponse<PageResult<List<ForumArticleVO>>> loadUserArticle(@RequestBody LoadUserArticleDto dto) {
        ForumArticleVO forumArticleVO = new ForumArticleVO();

        // TODO：需要修改，类型 0:发帖 1:评论过的文章  2:点赞过的文章
        // 1. 如果是类型 0 发帖，去查询发帖的 forum_article 表
        // 2. 如果是 1 / 2，再去查 like_record 表
        PageHelper.startPage(forumArticleVO.getPageNo(), forumArticleVO.getPageSize());

        List<ForumArticleVO> list = new ArrayList<>();

        Collections.sort(list, (article1, article2) -> article2.getPostTime().compareTo(article1.getPostTime()));

        PageInfo<ForumArticleVO> page = new PageInfo<>(list);
        PageResult<List<ForumArticleVO>> result = new PageResult<>();

        // 1.先根据 vo 里面的 userId 和 type 到 like_record 找到对应的数据 List
        Long userId = dto.getUserId();
        int type = dto.getType();

        QueryWrapper<LikeRecord> queryWrapper = new QueryWrapper<>();

        queryWrapper.eq("user_id", userId).eq("op_type", type);
        List<LikeRecord> likeRecords = likeRecordMapper.selectList(queryWrapper);
        for (LikeRecord record : likeRecords) {
            // 2. 遍历 List 找到 object_id 然后再去 forum_article 找到对应的文章
            ForumArticle article = forumArticleMapper.selectById(record.getObjectId());
            ForumArticleVO articleVO = new ForumArticleVO(); // 创建新的实例
            BeanUtils.copyProperties(article, articleVO);
            list.add(articleVO);
        }

        SetPageUtils.setPageResult(page, result);
        MyResponse<PageResult<List<ForumArticleVO>>> response = new MyResponse<>();
        SetResponseUtils.setResponseSuccess(response, result);

        return response;
    }
}
