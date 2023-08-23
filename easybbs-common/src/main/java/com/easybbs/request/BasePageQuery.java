package com.easybbs.request;

import lombok.Data;
import lombok.Getter;

@Getter
public class BasePageQuery {
    private static final Integer MIN_PAGE_NO = 1;
    private static final Integer MIN_PAGE_SIZE = 10;
    private Integer pageNo = MIN_PAGE_NO;
    private Integer pageSize = MIN_PAGE_SIZE;

    public BasePageQuery() {
        if (pageNo < MIN_PAGE_NO) {
            pageNo = MIN_PAGE_NO;
        }
        if (pageSize < MIN_PAGE_SIZE) {
            pageSize = MIN_PAGE_SIZE;
        }
    }

    public void setPageNo(Integer pageNo) {
        if (pageNo < MIN_PAGE_NO) {
            this.pageNo = MIN_PAGE_NO;
        }
        this.pageNo = pageNo;
    }

    public void setPageSize(Integer pageSize) {
        if (pageSize < MIN_PAGE_SIZE) {
            this.pageSize = MIN_PAGE_SIZE;
        }
        this.pageNo = pageSize;
    }
}
