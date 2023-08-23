package com.easybbs.utils;

import com.github.pagehelper.PageInfo;
import com.easybbs.response.PageResult;

public class SetPageUtils {

    public static void setPageResult(PageInfo page, PageResult result) {
        result.setPageTotal((long) page.getPages());
        result.setPageNo(page.getPageNum());
        result.setPageSize((long) page.getPageSize());
        result.setTotalCount(page.getTotal());
        result.setList(page.getList());
    }
}