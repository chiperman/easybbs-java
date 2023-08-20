package com.easybbs.controller;

import com.easybbs.dto.UserArticleCount;
import com.easybbs.entity.UserInfo;
import com.easybbs.mapper.ForumArticleMapper;
import com.easybbs.request.UserIdRequest;
import com.easybbs.service.ForumArticleService;
import com.easybbs.service.UserInfoService;
import com.easybbs.vo.UserInfoResponseVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ucenter")
public class UserContoller {

    @Autowired
    UserInfoService userInfoService;

    @Autowired
    ForumArticleService forumArticleService;

    @Autowired
    ForumArticleMapper forumArticleMapper;


    @RequestMapping(value = "/getUserInfo", method = RequestMethod.POST)
    public UserInfoResponseVO getUserInfo(@RequestBody UserIdRequest userIdRequest) {
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

        return userInfoResponseVO;
    }

    private UserInfo getUserInfo(Long userId) {
        UserInfo userInfo = userInfoService.getById(userId);
        return userInfo;
    }


    private UserArticleCount getUserArticleCount(Long userId) {
        return forumArticleMapper.getUserArticleCount(userId);
    }
}
