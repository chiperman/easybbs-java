package com.easybbs.controller;

import com.easybbs.entity.UserInfo;
import com.easybbs.mapper.UserInfoMapper;
import com.easybbs.request.UserIdRequest;
import com.easybbs.service.UserInfoService;
import com.easybbs.vo.UserInfoResponseVO;
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
    UserInfoMapper userInfoMapper;


    @RequestMapping(value = "/getUserInfo", method = RequestMethod.POST)
    public UserInfoResponseVO getUserInfo(@RequestBody UserIdRequest userIdRequest) {
        UserInfoResponseVO userInfoResponseVO = new UserInfoResponseVO();
        Long userId = userIdRequest.getUserId();
        System.out.println("userId=" + userId);
        // 首先获得用户信息
        UserInfo userInfo = userInfoService.getById(userId);
        // UserInfo userInfo = userInfoMapper.selectById(userId);
        System.out.println(userInfo);


        return userInfoResponseVO;
    }
}
