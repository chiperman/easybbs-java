package com.easybbs.controller;

import com.easybbs.cconst.EHttpCode;
import com.easybbs.entity.UserInfo;
import com.easybbs.mapper.UserInfoMapper;
import com.easybbs.vo.UserQueryVo;
import com.easybbs.vo.UserUpdateVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.easybbs.response.MyResponse;
import com.easybbs.response.PageResult;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserInfoMapper mapper;

    @RequestMapping("/loadUserList")
    public MyResponse<PageResult<UserInfo>> getUserList(HttpServletRequest request, @RequestBody UserQueryVo vo) {
        PageHelper.startPage(vo.getPageNo(), vo.getPageSize());
        List<UserInfo> lists = mapper.loadUserList(vo);
        PageInfo<UserInfo> page = new PageInfo<>(lists);

        PageResult<UserInfo> result = new PageResult();
        setPageResult(page, result);

        MyResponse<PageResult<UserInfo>> response = new MyResponse<>();
        response.setCode(EHttpCode.SUCCESS.getCode());
        response.setInfo(EHttpCode.SUCCESS.getInfo());
        response.setStatus(EHttpCode.SUCCESS.getStatus());
        response.setData(result);
        return response;
    }

    @RequestMapping("/updateUserStatus")
    public MyResponse updateUserStatus(HttpServletRequest request, @RequestBody UserUpdateVo vo) {
        int result = mapper.updateStatus(vo);
        MyResponse response = new MyResponse();
        if (result != 1) {
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

    //    @RequestMapping("/sendMessage")
    //    public MyResponse sendMessage(HttpServletRequest request, @RequestBody UserMsgVo vo) {
    //        int result = mapper.updateStatus(vo);
    //        MyResponse response = new MyResponse();
    //        if (result != 1) {
    //            response.setStatus(EHttpCode.FAIL.getStatus());
    //            response.setCode(EHttpCode.FAIL.getCode());
    //            response.setInfo(EHttpCode.FAIL.getInfo());
    //        } else {
    //            response.setStatus(EHttpCode.SUCCESS.getStatus());
    //            response.setCode(EHttpCode.SUCCESS.getCode());
    //            response.setInfo(EHttpCode.SUCCESS.getInfo());
    //        }
    //        return response;
    //    }


    private void setPageResult(PageInfo page, PageResult result) {
        result.setPageTotal((long) page.getPages());
        result.setPageNo(page.getPageNum());
        result.setPageSize((long) page.getPageSize());
        result.setTotalCount(page.getTotal());
        result.setList(page.getList());
    }
}
