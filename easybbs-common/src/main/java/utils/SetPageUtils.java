package utils;

import com.github.pagehelper.PageInfo;
import response.PageResult;

public class SetPageUtils {

    public static void setPageResult(PageInfo page, PageResult result) {
        result.setPageTotal(page.getPages());
        result.setPageNo(page.getPageNum());
        result.setPageSize(page.getPageSize());
        result.setTotalCount(page.getTotal());
        result.setList(page.getList());
    }
}