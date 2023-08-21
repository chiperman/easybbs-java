package com.easybbs.controller;

import com.easybbs.dto.UserArticleCount;
import com.easybbs.dto.UserIntegralDto;
import com.easybbs.entity.UserInfo;
import com.easybbs.entity.UserIntegralRecord;
import com.easybbs.mapper.ForumArticleMapper;
import com.easybbs.request.UserIdRequest;
import com.easybbs.service.ForumArticleService;
import com.easybbs.service.UserInfoService;
import com.easybbs.service.UserIntegralRecordService;
import com.easybbs.vo.UserInfoResponseVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import response.MyResponse;
import response.PageResult;
import utils.SetPageUtils;
import utils.SetResponseUtils;

import java.util.List;

@RestController
@RequestMapping("/ucenter")
public class UserController {

    @Autowired
    UserInfoService userInfoService;

    @Autowired
    ForumArticleService forumArticleService;

    @Autowired
    ForumArticleMapper forumArticleMapper;

    @Autowired
    UserIntegralRecordService userIntegralRecordService;


    /**
     * 获取用户信息
     *
     * @param userIdRequest
     *
     * @return
     */
    @RequestMapping(value = "/getUserInfo", method = RequestMethod.POST)
    public MyResponse<UserInfoResponseVO> getUserInfo(@RequestBody UserIdRequest userIdRequest) {
        MyResponse<UserInfoResponseVO> response = new MyResponse<>();
        Long userId = userIdRequest.getUserId();
        UserInfoResponseVO userInfoResponseVO = new UserInfoResponseVO();
        // 根据 userId 获得用户信息
        UserInfo userInfo = getUserInfo(userId);

        // 将 userInfo 复制到 userInfoResponseVO
        BeanUtils.copyProperties(userInfo, userInfoResponseVO);

        // 查询该用户的发帖数和收到的点赞数
        // int postCount = getPostCount(userId);

        UserArticleCount userArticleCount = getUserArticleCount(userId);
        userInfoResponseVO.setPostCount(userArticleCount.getPostCount());
        userInfoResponseVO.setLikeCount(userArticleCount.getLikeCount());

        SetResponseUtils.setResponseSuccess(response, userInfoResponseVO);
        return response;
    }

    private UserInfo getUserInfo(Long userId) {
        return userInfoService.getById(userId);
    }


    private UserArticleCount getUserArticleCount(Long userId) {
        return forumArticleMapper.getUserArticleCount(userId);
    }


    /**
     * 积分记录
     *
     * @return
     */
    @RequestMapping(value = "/loadUserIntegralRecord", method = RequestMethod.POST)
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


    @RequestMapping(value = "/updateUserInfo", method = RequestMethod.POST)
    public MyResponse updateUserInfo() {
        return new MyResponse<>();
    }
}
