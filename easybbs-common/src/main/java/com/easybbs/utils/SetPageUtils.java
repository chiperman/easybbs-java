package com.easybbs.utils;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.pagehelper.PageInfo;
import com.easybbs.response.PageResult;

import java.util.List;

public class SetPageUtils {

    public static void setPageResult(PageInfo page, PageResult result) {
        result.setPageTotal((long) page.getPages());
        result.setPageNo(page.getPageNum());
        result.setPageSize((long) page.getPageSize());
        result.setTotalCount(page.getTotal());
        result.setList(page.getList());
    }

    public static void setPlusPageResult(Integer pageNo, List resultDtos, Page page, PageResult result) {
        result.setPageNo(pageNo);
        result.setPageSize(page.getSize());
        result.setPageTotal(page.getPages());
        result.setTotalCount(page.getTotal());
        result.setList(resultDtos);
    }
}